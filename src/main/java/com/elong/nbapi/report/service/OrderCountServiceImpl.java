package com.elong.nbapi.report.service;

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
import com.elong.nbapi.report.dao.OrderCountDao;
import com.elong.nbapi.report.model.OrderCountModel;

@Service
public class OrderCountServiceImpl {

	@Resource
	private OrderCountDao dao;

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
	
	public List<String[]> getAllProxyCountData(String countdate){
		List<String[]> data = new LinkedList<String[]>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date countday = null;
		try {
			countday = sdf.parse(countdate);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("error occurs parsing the date:" + countdate);
		}
		
		Date yesterday = new Date(countday.getTime() - 24 * 60 * 60 * 1000);
		String countyesterday = sdf.format(yesterday);
		
		Date lastweekday = new Date(countday.getTime() - 7 * 24 * 60 * 60 * 1000);
		String countlastweekday = sdf.format(lastweekday);
		
		
		List<OrderCountModel> records = dao.getOrderCount(countdate);

		List<OrderCountModel> yesterday_records = dao.getOrderCount(countyesterday);
		
		List<OrderCountModel> week_records = dao.getOrderCount(countlastweekday);
		
		Collections.sort(records, new Comparator<OrderCountModel>(){

			@Override
			public int compare(OrderCountModel o1, OrderCountModel o2) {
				return o2.getCreasum() - o1.getCreasum();
			}
			
		});
		
		Map<String, OrderCountModel> yesterday_map = toMap(yesterday_records);
		Map<String, OrderCountModel> week_map = toMap(week_records);
		
		String[] titles = new String[]{"", "请求数", "DOD", "WOW", "可定通过率", "DOD", "WOW", 
											"请求数", "DOD", "WOW","成单通过率", "DOD", "WOW"};
		data.add(titles);
		
        NumberFormat numberFormat = NumberFormat.getInstance();  
        numberFormat.setMaximumFractionDigits(2); 
        
		for (OrderCountModel qcr : records){
			String[] line = new String[13];
			line[0] = UserServiceAgent.getProjectNameByProxyId(qcr.getProxyid());
			
			//--------------------------COUNT DAY------------------------
			line[1] = "" + qcr.getValisum();
			if (qcr.getValisum() == 0)
				line[4] = "N/A";
			else{
				float a = qcr.getValisuccess();
				float b = qcr.getValisum();
				line[4] = numberFormat.format(a / b * 100) + "%";
			}
				
			line[7] = "" + qcr.getCreasum();
			if (qcr.getCreasum() == 0)
				line[10] = "N/A";
			else{
				float a = qcr.getCreasuccess();
				float b = qcr.getCreasum();
				line[10] = numberFormat.format(a / b * 100) + "%";
			}

			
			//--------------------------DOD------------------------
			OrderCountModel qcr_yesterday = yesterday_map.get(qcr.getProxyid());
			if (qcr_yesterday == null){
				line[2] = "N/A";
				line[5] = "N/A";
				line[8] = "N/A";
				line[11] = "N/A";
			}else{
				if (qcr_yesterday.getValisum() == 0)
					line[2] = "N/A";
				else{
					float a = qcr.getValisum();
					float b = qcr_yesterday.getValisum();
					line[2] = numberFormat.format(a / b * 100) + "%";
				}
				
				if (qcr.getValisum() == 0 || qcr_yesterday.getValisuccess() == 0 || qcr_yesterday.getValisum() == 0)
					line[5] = "N/A";
				else{
					float a = qcr.getValisuccess();
					float b = qcr.getValisum();
					float aa = qcr_yesterday.getValisuccess();
					float bb = qcr_yesterday.getValisum();
					line[5] = numberFormat.format((a / b) / (aa / bb) * 100) + "%";
				}

				
				if (qcr_yesterday.getCreasum() == 0)
					line[8] = "N/A";
				else{
					float a = qcr.getCreasum();
					float b = qcr_yesterday.getCreasum();
					line[8] = numberFormat.format(a / b * 100) + "%";
				}
				
				if (qcr.getCreasum() == 0 || qcr_yesterday.getCreasuccess() == 0 || qcr_yesterday.getCreasum() == 0)
					line[11] = "N/A";
				else{
					float a = qcr.getCreasuccess();
					float b = qcr.getCreasum();
					float aa = qcr_yesterday.getCreasuccess();
					float bb = qcr_yesterday.getCreasum();
					line[11] = numberFormat.format((a / b) / (aa / bb) * 100) + "%";
				}
			}
			//--------------------------WOW------------------------
			OrderCountModel qcr_week = week_map.get(qcr.getProxyid());
			if (qcr_week == null){
				line[3] = "N/A";
				line[6] = "N/A";
				line[9] = "N/A";
				line[12] = "N/A";
			}else{
				if (qcr_week.getValisum() == 0)
					line[3] = "N/A";
				else{
					float a = qcr.getValisum();
					float b = qcr_week.getValisum();
					line[3] = numberFormat.format(a / b * 100) + "%";
				}
				
				if (qcr.getValisum() == 0 || qcr_week.getValisuccess() == 0 || qcr_week.getValisum() == 0)
					line[6] = "N/A";
				else{
					float a = qcr.getValisuccess();
					float b = qcr.getValisum();
					float aa = qcr_week.getValisuccess();
					float bb = qcr_week.getValisum();
					line[6] = numberFormat.format((a / b) / (aa / bb) * 100) + "%";
				}

				
				if (qcr_week.getCreasum() == 0)
					line[9] = "N/A";
				else{
					float a = qcr.getCreasum();
					float b = qcr_week.getCreasum();
					line[9] = numberFormat.format(a / b * 100) + "%";
				}
				
				if (qcr.getCreasum() == 0 || qcr_week.getCreasuccess() == 0 || qcr_week.getCreasum() == 0)
					line[12] = "N/A";
				else{
					float a = qcr.getCreasuccess();
					float b = qcr.getCreasum();
					float aa = qcr_week.getCreasuccess();
					float bb = qcr_week.getCreasum();
					line[12] = numberFormat.format((a / b) / (aa / bb) * 100) + "%";
				}
			}
			data.add(line);
		}
		return data;
	}

	private static Map<String, OrderCountModel> toMap(List<OrderCountModel> records){
		Map<String, OrderCountModel> map = new HashMap<String, OrderCountModel>();
		for (OrderCountModel r : records){
			map.put(r.getProxyid(), r);
		}
		return map;
	}
	

}
