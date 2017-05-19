package com.elong.nbapi.report.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Repository;

import com.elong.nbapi.report.model.OrderCountRecord;

@Repository
public class OrderCountHDFSDao extends HDFSDaoBase{
	
	public OrderCountHDFSDao() throws IOException, URISyntaxException {
		super();
	}

	public List<OrderCountRecord> getOrderCount(Map<String, String> params) {
		String countdate = params.get("countdate");
		List<OrderCountRecord> list = new LinkedList<OrderCountRecord>();
		try {
			InputStream in = fs.open(new Path(
					"/data/input/northbound/checklist-result/ordercount/" + countdate
							+ "/part-r-00000"));
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(reader);

			String str = null;

			while ((str = br.readLine()) != null) {
				OrderCountRecord rd = new OrderCountRecord();
				String[] key_count = str.split("\t");
				
				if (key_count.length != 9){
					System.err.println(str);
					continue;
				}
				
				rd.setProxyid(key_count[0]);
				rd.setValisum(Integer.valueOf(key_count[1]));
				rd.setValisuccess(Integer.valueOf(key_count[2]));
				rd.setValifailed(Integer.valueOf(key_count[3]));
				rd.setCreasum(Integer.valueOf(key_count[4]));
				rd.setCreasuccess(Integer.valueOf(key_count[5]));
				rd.setCreafailed(Integer.valueOf(key_count[6]));
				rd.setPrepay(Integer.valueOf(key_count[7]));
				rd.setSelfpay(Integer.valueOf(key_count[8]));
				
				list.add(rd);
			}

			br.close();
			reader.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getResultDate() {
		List<String> list = new LinkedList<String>();
		try {
			FileStatus[] listiter = fs.listStatus(new Path("/data/input/northbound/checklist-result/ordercount/"));
			for (FileStatus f : listiter){
				if (f.isFile()) continue;
				String name = f.getPath().getName();
				list.add(name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		OrderCountHDFSDao dao = new OrderCountHDFSDao();
		Map<String, String> params = new HashMap<String, String>();
		params.put("countdate", "20170417");

		List<String> list = dao.getResultDate();
		list.size();
	}

}
