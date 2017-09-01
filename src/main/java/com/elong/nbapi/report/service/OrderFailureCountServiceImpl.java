package com.elong.nbapi.report.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elong.nb.UserServiceAgent;
import com.elong.nbapi.report.dao.OrderFailureCountDao;
import com.elong.nbapi.report.model.OrderFailureModel;

@Service
public class OrderFailureCountServiceImpl {

	@Resource
	private OrderFailureCountDao dao;

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
	
	public Map<String, List<String[]>> getAllProxyCountData(String countdate){
		
		List<OrderFailureModel> records = dao.getFailureCount(countdate);

		Map<String, List<OrderFailureModel>> hm = new HashMap<String, List<OrderFailureModel>>();
		for (OrderFailureModel m : records){
			List<OrderFailureModel> ls = null;
			if (hm.containsKey(m.getProxyOrderFrom())) ls = hm.get(m.getProxyOrderFrom());
			else{
				ls = new LinkedList<OrderFailureModel>();
				hm.put(m.getProxyOrderFrom(), ls);
			} 
			ls.add(m);
		}

		
		Map<String, List<String[]>> rst = new HashMap<String, List<String[]>>();
		for (String key : hm.keySet()){
			List<OrderFailureModel> ls = hm.get(key);
			Collections.sort(ls, new Comparator<OrderFailureModel>(){

				@Override
				public int compare(OrderFailureModel o1, OrderFailureModel o2) {
					return o2.getCount() - o1.getCount();
				}
				
			});
			
			String projectName = UserServiceAgent.getProjectNameByOrderFrom(key);
			
			List<String[]> lines = new LinkedList<String[]>();
			for (OrderFailureModel c : ls){
				String[] line = new String[]{c.getFailure(), "" + c.getCount()};
				lines.add(line);
			}
			
			if (rst.containsKey(projectName))
				rst.get(projectName).addAll(lines);
			else
				rst.put(projectName, lines);
		}

		return rst;
	}


	

}
