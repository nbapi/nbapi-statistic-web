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
import com.elong.nb.common.model.ProxyAccount;
import com.elong.nbapi.report.dao.CheckFailureCountDao;
import com.elong.nbapi.report.model.CheckFailureModel;

@Service
public class CheckFailureCountServiceImpl {

	@Resource
	private CheckFailureCountDao dao;

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
		
		List<CheckFailureModel> records = dao.getFailureCount(countdate);

		Map<String, List<CheckFailureModel>> hm = new HashMap<String, List<CheckFailureModel>>();
		for (CheckFailureModel m : records){
			List<CheckFailureModel> ls = null;
			if (hm.containsKey(m.getProxyOrderFrom())) ls = hm.get(m.getProxyOrderFrom());
			else{
				ls = new LinkedList<CheckFailureModel>();
				hm.put(m.getProxyOrderFrom(), ls);
			} 
			ls.add(m);
		}

		
		Map<String, List<String[]>> rst = new HashMap<String, List<String[]>>();
		for (String key : hm.keySet()){
			List<CheckFailureModel> ls = hm.get(key);
			Collections.sort(ls, new Comparator<CheckFailureModel>(){

				@Override
				public int compare(CheckFailureModel o1, CheckFailureModel o2) {
					return o2.getCount() - o1.getCount();
				}
				
			});
			
			ProxyAccount pa = UserServiceAgent.findProxyByOrderfrom(key);	
			String projectName = pa == null ? key : pa.getProjectName();
			
			List<String[]> lines = new LinkedList<String[]>();
			for (CheckFailureModel c : ls){
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
