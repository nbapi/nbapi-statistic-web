package com.elong.nbapi.report.dao;

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
import com.elong.nbapi.report.model.MethodCountModel;

@Repository
public class MethodCountDao {

	private String url;

	private String username;

	public MethodCountDao() {
		Properties p = CommonsUtil
				.loadProperties("conf/custom/env/jdbc.properties");
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

	public List<MethodCountModel> findMethodCount(String datesource) {
		List<MethodCountModel> result = new ArrayList<MethodCountModel>();
		Connection cnct = null;
		PreparedStatement ps = null;
		ResultSet rset = null;
		try {
			cnct = DriverManager.getConnection(url, username, "");
			ps = cnct
					.prepareStatement("select * from app_proxyMethodCount where ds=?");
			ps.setString(1, datesource);
			rset = ps.executeQuery();
			while (rset.next()) {
				MethodCountModel p = new MethodCountModel();
				p.setProxyId(rset.getString(1));
				p.setMethodName(rset.getString(2));
				p.setCount(rset.getLong(3));
				p.setDs(rset.getString(4));
				result.add(p);
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
