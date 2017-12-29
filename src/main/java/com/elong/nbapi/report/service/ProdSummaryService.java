package com.elong.nbapi.report.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.elong.nbapi.common.dao.impl.BaseDao;
import com.elong.nbapi.common.po.BusinessSystem;
import com.elong.nbapi.common.po.ReportSystem;
import com.elong.nbapi.report.dao.ProdSummaryDao;

@Service
public class ProdSummaryService {

	@Resource
	private ProdSummaryDao dao;
	@Resource
	private BaseDao baseDao;
	
	public <T> Map<String, Object>queryAllProdSummary(String ds,String id){
		ReportSystem findOne = getReportSystem(id);
		String reportSQL = findOne.getReportSQL();
		String aliasNames = findOne.getAliasNames();
		String[] titles = aliasNames.split(",");
		List<String> cols = Arrays.asList(findOne.getReportCols().split(","));
		Map<String, Object> result = new HashMap<>();
		List<T> records = dao.executSQL(ds, reportSQL);
		List<String[]> data = new LinkedList<String[]>();
		for (T t : records) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> map = (HashMap<String, Object>) t;
			List<String> lineList = new ArrayList<String>();
			for (String col : cols) {
				lineList.add(map.get(col).toString());
			}
			String[] line = new String[lineList.size()];
			data.add(lineList.toArray(line));
		}
		result.put("title", titles);
		result.put("data", data);
		return result;
	}
	
	public ReportSystem getReportSystem(String id){
		 Criteria criteria = Criteria.where(BusinessSystem.FIELD_ID).is(id);
	     Query query = Query.query(criteria);
	     return baseDao.findOne(query, ReportSystem.class);
	}

}
