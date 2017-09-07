package com.elong.nbapi.prod.logentity.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elong.nb.UserServiceAgent;
import com.elong.nb.common.model.ProxyAccount;
import com.elong.nbapi.prod.logentity.dao.LogEntityDao;
import com.elong.nbapi.prod.logentity.model.DayLogEntityModel;
import com.elong.nbapi.prod.logentity.model.SummaryLogEntityModel;
import com.elong.nbapi.prod.logentity.util.CategoryUtil;

@Service
public class LogEntityService {

	@Resource
	private LogEntityDao dao;
	
	public Map<String, Object> queryAllSummary(String startDs, String endDs){
		List<SummaryLogEntityModel> ls = dao.queryAllSummary(startDs, endDs);
		
		Map<String, Long> ds_times = new TreeMap<String, Long>(new DSComparator());
		Map<String, TreeMap<String, String>> user_times = new TreeMap<String, TreeMap<String, String>>(new DSComparator());
		
		for (SummaryLogEntityModel slem : ls){
			//求和
			Long l = ds_times.get(slem.getDs());
			if (l == null) l = 0l;
			ds_times.put(slem.getDs(), l + slem.getDaySumCount());
			
			//分类user
			if (!user_times.containsKey(slem.getProxyId())){
				TreeMap<String, String> tmp = new TreeMap<String, String>(new DSComparator());
				user_times.put(slem.getProxyId(), tmp);
			}
			user_times.get(slem.getProxyId()).put(slem.getDs(), "" + slem.getDaySumCount());
			
		}
		
		//形成summary
		Object[] summary = new Object[ds_times.size() * 2];
		int i = 0;
		for (Entry<String, Long> e : ds_times.entrySet()){
			summary[i++] = e.getKey();
			summary[i++] = e.getValue();
		}
		
		//形成明细数据
		List<String> titles = new LinkedList<String>();
		titles.add("");
		titles.addAll(ds_times.keySet());
		
		List<String[]> table = new LinkedList<String[]>();
		for (String user : user_times.keySet()){
			List<String> line = new LinkedList<String>(); 
			ProxyAccount pa = UserServiceAgent.findProxyByUsername(user);
			line.add(pa == null ? user : pa.getProjectName());
			Map<String, String> tmp_ds_times = user_times.get(user);
			for (String ds : ds_times.keySet()){
				line.add(tmp_ds_times.containsKey(ds) ? tmp_ds_times.get(ds) : "0");
			}
			table.add(line.toArray(new String[line.size()]));
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("summary", summary);
		result.put("title", titles.toArray(new String[titles.size()]));
		result.put("data", table);
		return result;
	}
	
	public Map<String, Object> queryAllDay(String ds){
		List<DayLogEntityModel> ls = dao.queryAllDay(ds);
		
		Set<String> methods = new TreeSet<String>(new DSComparator());
		Map<String, TreeMap<String, Long>> category_times = new TreeMap<String, TreeMap<String, Long>>(new DSComparator());
		Map<String, TreeMap<String, String>> user_times = new TreeMap<String, TreeMap<String, String>>(new DSComparator());
		
		for (DayLogEntityModel dlem : ls){
			//方法表
			methods.add(dlem.getMethodName());
			//生成分类图表数据
			String category = CategoryUtil.category(dlem.getProxyId());
			if (!category_times.containsKey(category)){
				TreeMap<String, Long> tmp = new TreeMap<String, Long>(new DSComparator());
				category_times.put(category, tmp);
			}
			Long times = category_times.get(category).get(dlem.getMethodName());
			if (times == null) times = 0l;
			category_times.get(category).put(dlem.getMethodName(), times + dlem.getDaySumCount());
			
			//生产user数据
			if (!user_times.containsKey(dlem.getProxyId())){
				TreeMap<String, String> tmp = new TreeMap<String, String>(new DSComparator());
				user_times.put(dlem.getProxyId(), tmp);
			}
			user_times.get(dlem.getProxyId()).put(dlem.getMethodName(), "" + dlem.getDaySumCount());
		}
		
		//category_times补0
		for (TreeMap<String, Long> times : category_times.values()){
			for (String m : methods){
				if (!times.containsKey(m)) times.put(m, 0l);
			}
		}
		
		//形成明细数据
		List<String> titles = new LinkedList<String>();
		titles.add("");
		titles.addAll(methods);
		
		List<String[]> table = new LinkedList<String[]>();
		for (String user : user_times.keySet()){
			List<String> line = new LinkedList<String>(); 
			ProxyAccount pa = UserServiceAgent.findProxyByUsername(user);
			line.add(pa == null ? user : pa.getProjectName());
			Map<String, String> tmp_method_times = user_times.get(user);
			for (String method : methods){
				line.add(tmp_method_times.containsKey(method) ? tmp_method_times.get(method) : "0");
			}
			table.add(line.toArray(new String[line.size()]));
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("chart", category_times);
		result.put("title", titles.toArray(new String[titles.size()]));
		result.put("data", table);
		return result;
	}
	
	
	
	private class DSComparator implements Comparator<String>{

		@Override
		public int compare(String o1, String o2) {
			return o1.compareTo(o2);
		}
		
	}

	
}
