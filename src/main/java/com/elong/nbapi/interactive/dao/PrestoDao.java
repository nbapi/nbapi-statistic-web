package com.elong.nbapi.interactive.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Repository;

import com.elong.nb.common.util.CommonsUtil;
import com.facebook.presto.jdbc.PrestoConnection;
import com.facebook.presto.jdbc.PrestoResultSet;
import com.facebook.presto.jdbc.PrestoStatement;

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

	public List<String> showTables(){
		List<String> result = new ArrayList<String>();
		Connection cnct = null;
		Statement stat = null;
		ResultSet rset = null;
		try {
			cnct = DriverManager.getConnection(url, username, "");
			stat = cnct.createStatement();
			rset = stat.executeQuery("show tables");
			while (rset.next()) {
				result.add(rset.getString(1));
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

	public List<String[]> exeSQL(String sql) {
		List<String[]> result = new ArrayList<String[]>();
		PrestoConnection pc = null;
		PrestoStatement ps = null;
		PrestoResultSet rset = null;
		try {
			pc = (PrestoConnection)(DriverManager.getConnection(url, username, ""));
			ps = (PrestoStatement)(pc.createStatement());
			rset = (PrestoResultSet)(ps.executeQuery(sql));
			int c = rset.getMetaData().getColumnCount();
			if (c <= 0) throw new RuntimeException("Empty column!");
			String[] titles = new String[c];
			for (int i=1; i<=c; i++){
				titles[i-1] = rset.getMetaData().getColumnName(i);
			}
			result.add(titles);
			while (rset.next()) {
				String[] row = new String[c];
				for (int i=1; i<=c; i++){
					row[i-1] = rset.getString(i);
				}
				result.add(row);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (rset != null)
					rset.close();
				if (ps != null)
					ps.close();
				if (pc != null)
					pc.close();
			} catch (Exception ee) {
			}
		}
		return result;
	}
}