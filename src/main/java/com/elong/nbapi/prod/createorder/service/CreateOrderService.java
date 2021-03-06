package com.elong.nbapi.prod.createorder.service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elong.nb.UserServiceAgent;
import com.elong.nb.common.model.ProxyAccount;
import com.elong.nbapi.prod.createorder.dao.CreateOrderDao;
import com.elong.nbapi.prod.createorder.model.CreateOrderCountModel;
import com.elong.nbapi.prod.createorder.model.CreateOrderErrorModel;

@Service
public class CreateOrderService {

	@Resource
	private CreateOrderDao dao;
	
	public Map<String, Object> queryAllCreateOrder(String ds){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date countday = null;
		try {
			countday = sdf.parse(ds);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("error occurs parsing the date:" + ds);
		}
		Date yesterday = new Date(countday.getTime() - 24 * 60 * 60 * 1000);
		String countyesterday = sdf.format(yesterday);
		Date lastweekday = new Date(countday.getTime() - 7 * 24 * 60 * 60 * 1000);
		String countlastweekday = sdf.format(lastweekday);
		
		List<CreateOrderCountModel> records = dao.queryAllCreateOrder(ds);
		List<CreateOrderCountModel> yesterday_records = dao.queryAllCreateOrder(countyesterday);
		List<CreateOrderCountModel> week_records = dao.queryAllCreateOrder(countlastweekday);
		
		Collections.sort(records, new Comparator<CreateOrderCountModel>(){

			@Override
			public int compare(CreateOrderCountModel o1, CreateOrderCountModel o2) {
				return (int)(o2.getDaySumCount() - o1.getDaySumCount());
			}
			
		});
		
		Map<String, CreateOrderCountModel> yesterday_map = toMap(yesterday_records);
		Map<String, CreateOrderCountModel> week_map = toMap(week_records);
		
		String[] titles = new String[]{"", "成单请求量", "DOD", "WOW","成单通过率", "DOD", "WOW","错误量"};
		
        NumberFormat numberFormat = NumberFormat.getInstance();  
        numberFormat.setMaximumFractionDigits(2); 
		
        long sumcount = 0;
        long successcount = 0;
        
        List<String[]> data = new LinkedList<String[]>();
		for (CreateOrderCountModel cocm : records){
			sumcount += cocm.getDaySumCount();
			successcount += cocm.getSuccessCount();
			
			String[] line = new String[9];
			line[0] = cocm.getUserName();
			ProxyAccount pa = UserServiceAgent.findProxyByUsername(cocm.getUserName());
			line[1] = pa == null ? cocm.getUserName() : pa.getProjectName();
			//--------------------------COUNT DAY------------------------
			line[2] = "" + cocm.getDaySumCount();
			if (cocm.getDaySumCount() == 0)
				line[5] = "N/A";
			else{
				float a = cocm.getSuccessCount();
				float b = cocm.getDaySumCount();
				line[5] = numberFormat.format(a / b * 100) + "%";
			}
			//--------------------------DOD------------------------
			CreateOrderCountModel cocm_yesterday = yesterday_map.get(cocm.getUserName());
			if (cocm_yesterday == null){
				line[3] = "N/A";
				line[6] = "N/A";
			}else{
				if (cocm_yesterday.getDaySumCount() == 0)
					line[3] = "N/A";
				else{
					float a = cocm.getDaySumCount();
					float b = cocm_yesterday.getDaySumCount();
					line[3] = numberFormat.format(a / b * 100) + "%";
				}
				
				if (cocm.getDaySumCount() == 0 || cocm_yesterday.getSuccessCount() == 0 || cocm_yesterday.getDaySumCount() == 0)
					line[6] = "N/A";
				else{
					float a = cocm.getSuccessCount();
					float b = cocm.getDaySumCount();
					float aa = cocm_yesterday.getSuccessCount();
					float bb = cocm_yesterday.getDaySumCount();
					line[6] = numberFormat.format((a / b) / (aa / bb) * 100) + "%";
				}
			}
			//--------------------------WOW------------------------
			CreateOrderCountModel cocm_week = week_map.get(cocm.getUserName());
			if (cocm_week == null){
				line[4] = "N/A";
				line[7] = "N/A";
			}else{
				if (cocm_week.getDaySumCount() == 0)
					line[4] = "N/A";
				else{
					float a = cocm.getDaySumCount();
					float b = cocm_week.getDaySumCount();
					line[4] = numberFormat.format(a / b * 100) + "%";
				}
				
				if (cocm.getDaySumCount() == 0 || cocm_week.getSuccessCount() == 0 || cocm_week.getDaySumCount() == 0)
					line[7] = "N/A";
				else{
					float a = cocm.getSuccessCount();
					float b = cocm.getDaySumCount();
					float aa = cocm_week.getSuccessCount();
					float bb = cocm_week.getDaySumCount();
					line[7] = numberFormat.format((a / b) / (aa / bb) * 100) + "%";
				}
			}
			line[8] = "" + (cocm.getDaySumCount() - cocm.getSuccessCount());
			data.add(line);
		}
		
		Object[] chart = new Object[]{"success", successcount, "failed", sumcount - successcount};
		
		Map<String, Object> result = new HashMap<>();
		result.put("chart", chart);
		result.put("title", titles);
		result.put("data", data);
		return result;
	}
	
	public Map<String, Object> queryAllError(String ds, String username){
		List<CreateOrderErrorModel> ls = dao.queryAllError(ds, username);
		
		List<Object[]> chart = new LinkedList<Object[]>();
		for (CreateOrderErrorModel coem : ls){
			chart.add(new Object[]{coem.getRespCode(), coem.getDaySumCount()});
		}

		Map<String, Object> result = new HashMap<>();
		result.put("chart", chart);
		return result;
	}
	
	private static Map<String, CreateOrderCountModel> toMap(List<CreateOrderCountModel> records){
		Map<String, CreateOrderCountModel> map = new HashMap<String, CreateOrderCountModel>();
		for (CreateOrderCountModel r : records){
			map.put(r.getUserName(), r);
		}
		return map;
	}

}
