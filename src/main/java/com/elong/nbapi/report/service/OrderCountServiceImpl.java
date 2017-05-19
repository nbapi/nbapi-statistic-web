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
import com.elong.nb.common.model.ProxyAccount;
import com.elong.nbapi.report.dao.OrderCountHDFSDao;
import com.elong.nbapi.report.model.OrderCountRecord;

@Service
public class OrderCountServiceImpl {

	@Resource
	private OrderCountHDFSDao dao;

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
		
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("countdate", countdate);
		List<OrderCountRecord> records = dao.getOrderCount(params);

		params.put("countdate", countyesterday);
		List<OrderCountRecord> yesterday_records = dao.getOrderCount(params);
		
		params.put("countdate", countlastweekday);
		List<OrderCountRecord> week_records = dao.getOrderCount(params);
		
		Collections.sort(records, new Comparator<OrderCountRecord>(){

			@Override
			public int compare(OrderCountRecord o1, OrderCountRecord o2) {
				return o2.getCreasum() - o1.getCreasum();
			}
			
		});
		
		Map<String, OrderCountRecord> yesterday_map = toMap(yesterday_records);
		Map<String, OrderCountRecord> week_map = toMap(week_records);
		
		String[] titles = new String[]{"", "请求数", "DOD", "WOW", "可定通过率", "DOD", "WOW", 
											"请求数", "DOD", "WOW","成单通过率", "DOD", "WOW",
											"订单数", "DOD", "WOW", "订单数", "DOD", "WOW"};
		data.add(titles);
		
        NumberFormat numberFormat = NumberFormat.getInstance();  
        numberFormat.setMaximumFractionDigits(2); 
        
		for (OrderCountRecord qcr : records){
			String[] line = new String[19];
			ProxyAccount pa = UserServiceAgent.findProxyByProxyId(qcr.getProxyid());
			line[0] = pa == null ? qcr.getProxyid() : pa.getProjectName();
			
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
			
			line[13] = "" + qcr.getPrepay();
			line[16] = "" + qcr.getSelfpay();
			
			//--------------------------DOD------------------------
			OrderCountRecord qcr_yesterday = yesterday_map.get(qcr.getProxyid());
			if (qcr_yesterday == null){
				line[2] = "N/A";
				line[5] = "N/A";
				line[8] = "N/A";
				line[11] = "N/A";
				line[14] = "N/A";
				line[17] = "N/A";
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
				
				if (qcr_yesterday.getPrepay() == 0)
					line[14] = "N/A";
				else{
					float a = qcr.getPrepay();
					float b = qcr_yesterday.getPrepay();
					line[14] = numberFormat.format(a / b * 100) + "%";
				}
				
				if (qcr_yesterday.getSelfpay() == 0)
					line[17] = "N/A";
				else{
					float a = qcr.getSelfpay();
					float b = qcr_yesterday.getSelfpay();
					line[17] = numberFormat.format(a / b * 100) + "%";
				}
			}
			//--------------------------WOW------------------------
			OrderCountRecord qcr_week = week_map.get(qcr.getProxyid());
			if (qcr_week == null){
				line[3] = "N/A";
				line[6] = "N/A";
				line[9] = "N/A";
				line[12] = "N/A";
				line[15] = "N/A";
				line[18] = "N/A";
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
				
				if (qcr_week.getPrepay() == 0)
					line[15] = "N/A";
				else{
					float a = qcr.getPrepay();
					float b = qcr_week.getPrepay();
					line[15] = numberFormat.format(a / b * 100) + "%";
				}
				
				if (qcr_week.getSelfpay() == 0)
					line[18] = "N/A";
				else{
					float a = qcr.getSelfpay();
					float b = qcr_week.getSelfpay();
					line[18] = numberFormat.format(a / b * 100) + "%";
				}
			}
			data.add(line);
		}
		return data;
	}

	private static Map<String, OrderCountRecord> toMap(List<OrderCountRecord> records){
		Map<String, OrderCountRecord> map = new HashMap<String, OrderCountRecord>();
		for (OrderCountRecord r : records){
			map.put(r.getProxyid(), r);
		}
		return map;
	}
	

}
