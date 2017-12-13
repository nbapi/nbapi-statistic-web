package com.elong.nbapi.interactive.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Repository;

import com.elong.nb.common.util.CommonsUtil;

@Repository
public class PrestoDao {

	private String url;

	private String username;

	public PrestoDao() {
		Properties p = CommonsUtil.loadProperties("conf/custom/env/jdbc_interactive.properties");
		try {
			Class.forName(p.getProperty("read.jdbc.driverClassName"));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		url = p.getProperty("read.jdbc.url");
		username = p.getProperty("read.jdbc.username");
	}

	public List<String> getResultDate() {
		List<String> result = new ArrayList<String>();
		Connection cnct = null;
		Statement stat = null;
		ResultSet rset = null;
		try {
			cnct = DriverManager.getConnection(url, username, "");
			stat = cnct.createStatement();
			rset = stat.executeQuery("show partitions app_proxymethodcount");
			while (rset.next()) {
				String ds_keyvalue = rset.getString(1);
				String[] keyvalue = ds_keyvalue.split("=");
				result.add(keyvalue[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rset != null)
					rset.close();
				if (stat != null)
					stat.close();
				if (cnct != null)
					cnct.close();
			} catch (Exception ee) {
			}
		}
		return result;
	}

	public List<String> findMethodCount(String datesource) {
		List<String> result = new ArrayList<String>();
		Connection cnct = null;
		Statement ps = null;
		ResultSet rset = null;
		try {
			cnct = DriverManager.getConnection(url, username, "");
			ps = cnct.createStatement();
			rset = ps.executeQuery("select * from app_methodcount_day where ds='" + datesource + "'");
			while (rset.next()) {
				StringBuffer sb = new StringBuffer();
				sb.append(rset.getString(1));
				result.add(sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rset != null)
					rset.close();
				if (ps != null)
					ps.close();
				if (cnct != null)
					cnct.close();
			} catch (Exception ee) {
			}
		}
		return result;
	}

}