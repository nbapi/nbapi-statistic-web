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

import com.elong.nbapi.report.model.MethodCountRecord;

@Repository
public class MethodCountHDFSDao extends HDFSDaoBase{
	
	public MethodCountHDFSDao() throws IOException, URISyntaxException {
		super();
	}

	public List<MethodCountRecord> getMethodCount(Map<String, String> params) {
		String countdate = params.get("countdate");
		List<MethodCountRecord> list = new LinkedList<MethodCountRecord>();
		try {
			InputStream in = fs.open(new Path(
					"/data/input/northbound/checklist-result/methodcount/" + countdate
							+ "/part-r-00000"));
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(reader);

			String str = null;

			while ((str = br.readLine()) != null) {

				String[] key_count = str.split("\t");
				String[] proxy_method = key_count[0].split("_");

				MethodCountRecord rd = new MethodCountRecord();
				rd.setAppname("nbapi"); // no use
				rd.setCountdate("xxxxxx"); // no use
				rd.setProxyid(proxy_method[0]);
				rd.setProxyname(proxy_method[0]); // no set
				rd.setMethod(proxy_method[1]);
				rd.setCount(Long.parseLong(key_count[1]));
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
			FileStatus[] listiter = fs.listStatus(new Path("/data/input/northbound/checklist-result/methodcount/"));
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
		MethodCountHDFSDao dao = new MethodCountHDFSDao();
		Map<String, String> params = new HashMap<String, String>();
		params.put("countdate", "20170417");

		List<String> list = dao.getResultDate();
		list.size();
	}

}
