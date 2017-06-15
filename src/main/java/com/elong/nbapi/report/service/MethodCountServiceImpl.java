package com.elong.nbapi.report.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elong.nb.UserServiceAgent;
import com.elong.nb.common.model.ProxyAccount;
import com.elong.nbapi.report.dao.MethodCountDao;
import com.elong.nbapi.report.model.MethodCountModel;

@Service
public class MethodCountServiceImpl {
	
	@Resource
	private MethodCountDao dao;

	public List<String> getResultDate(){
		List<String> list = dao.getResultDate();
		Collections.sort(list, new Comparator<String>(){

			@Override
			public int compare(String o1, String o2) {
				return o2.compareTo(o1);
			}
			
		});
		return list;
	}
	
	public List<String[]> getAllProxyCountData(String countdate) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("countdate", countdate);
		
		List<MethodCountModel> records = dao.findMethodCount(countdate);

		Map<String, Map<String, Long>> rst = new HashMap<String, Map<String, Long>>();
		if (records == null)
			return new LinkedList<String[]>();
		Set<String> methods = new LinkedHashSet<String>();
		methods.add("SUM");
		methods.add("createOrder"); // createOrder在第二列

		for (MethodCountModel r : records) {
			// 形成数据集
			if (!rst.containsKey(r.getProxyId())) {
				Map<String, Long> tmp = new HashMap<String, Long>();
				tmp.put("SUM", 0L);
				rst.put(r.getProxyId(), tmp);
			}
			rst.get(r.getProxyId()).put(r.getMethodName(), r.getCount());
			rst.get(r.getProxyId()).put("SUM",
					rst.get(r.getProxyId()).get("SUM") + r.getCount());

			// 形成方法集
			if (!methods.contains(r.getMethodName()))
				methods.add(r.getMethodName());
		}

		// 形成分销商集
		Map<String, String> proxys = sortProxys(rst);

		// 组织完整数据集
		List<String[]> data = new LinkedList<String[]>();
		String[] titles = new String[methods.size() + 1];
		titles[0] = ""; // 最左上角单元格
		int i = 1;
		for (String m : methods) {
			titles[i++] = m;
		}
		data.add(titles);

		for (Entry<String, String> entry : proxys.entrySet()) {
			String[] lines = new String[methods.size() + 1];
			lines[0] = entry.getValue();
			int j = 1;
			Map<String, Long> pm = rst.get(entry.getKey());
			for (String m : methods) {
				Long count = pm.get(m);
				lines[j++] = count == null ? "0" : "" + count;
			}
			data.add(lines);
		}

		return data;
	}

	private Map<String, String> sortProxys(
			final Map<String, Map<String, Long>> rstmap) {
		Map<String, String> proxys = new TreeMap<String, String>(
				new Comparator<String>() {
					public int compare(String key1, String key2) {
						Map<String, Long> p1 = rstmap.get(key1);
						Map<String, Long> p2 = rstmap.get(key2);
						if (p1 == null)
							return 1;
						if (p2 == null)
							return -1;
						Long i1 = p1.get("createOrder");
						Long i2 = p2.get("createOrder");
						if (i1 == null)
							return 1;
						if (i2 == null)
							return -1;
						if (i1 == i2)
							return 0;
						return i2 > i1 ? 1 : -1;
					}
				});

		for (String proxyid : rstmap.keySet()) {
			ProxyAccount pa = UserServiceAgent.findProxyByProxyId(proxyid);
			proxys.put(proxyid, pa == null ? proxyid : pa.getProjectName());
		}

		return proxys;
	}

}
