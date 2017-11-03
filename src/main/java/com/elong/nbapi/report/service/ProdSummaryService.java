package com.elong.nbapi.report.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elong.nbapi.report.dao.ProdSummaryDao;
import com.elong.nbapi.report.model.ProdSummary;

@Service
public class ProdSummaryService {

	@Resource
	private ProdSummaryDao dao;
	
	public Map<String, Object> queryAllProdSummary_q(String ds){
		List<ProdSummary> records = dao.queryAllProdSummary_q(ds);
		
		String[] titles = new String[]{"库存日期", "产品总数", "可卖产品数", "预付产品数","现付产品数", "房型数", "酒店数"};
		List<String[]> data = new LinkedList<String[]>();
		for (ProdSummary p : records){
			String[] line = new String[]{p.getInvDate(), "" + p.getProdCount(), "" + p.getSellProdCount(), "" + p.getPrePayCount(), "" + p.getSelfPayCount(), "" + p.getRoomTypeCount(), "" + p.getHotelCount()};
			data.add(line);
		}
		Map<String, Object> result = new HashMap<>();
		result.put("title", titles);
		result.put("data", data);
		return result;
	}
	
	public Map<String, Object> queryAllProdSummary_c(String ds){
		List<ProdSummary> records = dao.queryAllProdSummary_c(ds);
		
		String[] titles = new String[]{"库存日期", "产品总数", "可卖产品数", "预付产品数","现付产品数", "房型数", "酒店数"};
		List<String[]> data = new LinkedList<String[]>();
		for (ProdSummary p : records){
			String[] line = new String[]{p.getInvDate(), "" + p.getProdCount(), "" + p.getSellProdCount(), "" + p.getPrePayCount(), "" + p.getSelfPayCount(), "" + p.getRoomTypeCount(), "" + p.getHotelCount()};
			data.add(line);
		}
		Map<String, Object> result = new HashMap<>();
		result.put("title", titles);
		result.put("data", data);
		return result;
	}


}
