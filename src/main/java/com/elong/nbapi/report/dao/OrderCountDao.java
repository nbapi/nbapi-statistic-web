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
import com.elong.nbapi.report.model.OrderCountModel;

@Repository
public class OrderCountDao {

	private String url;

	private String username;

	public OrderCountDao() {
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
			rset = stat.executeQuery("show partitions app_proxyOrderCount");
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

	public List<OrderCountModel> getOrderCount(String datesource) {
		List<OrderCountModel> result = new ArrayList<OrderCountModel>();
		Connection cnct = null;
		PreparedStatement ps = null;
		ResultSet rset = null;
		try {
			cnct = DriverManager.getConnection(url, username, "");
			ps = cnct
					.prepareStatement("select * from app_proxyOrderCount where ds=?");
			ps.setString(1, datesource);
			rset = ps.executeQuery();
			while (rset.next()) {
				OrderCountModel o = new OrderCountModel();
				o.setProxyid(rset.getString(1));
				o.setValisum(rset.getInt(2));
				o.setValisuccess(rset.getInt(3));
				o.setValifailed(rset.getInt(4));
				o.setCreasum(rset.getInt(5));
				o.setCreasuccess(rset.getInt(6));
				o.setCreafailed(rset.getInt(7));
				result.add(o);
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
