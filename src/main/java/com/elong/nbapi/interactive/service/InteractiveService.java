package com.elong.nbapi.interactive.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elong.nbapi.interactive.dao.PrestoDao;

@Service
public class InteractiveService {

//	@Resource
//	private PrestoDao dao;

	public Map<String, Object> exeSQL(String sql) {
		if (sql != null && sql.toLowerCase().indexOf("drop") != -1) throw new RuntimeException("求不坑");
		Map<String, Object> result = new HashMap<>();
//		List<String[]> rst = dao.exeSQL(sql);
//		if (rst == null || rst.size() == 0)
//			return result;
//		String[] title = rst.remove(0);
//		result.put("title", title);
//		result.put("data", rst);
		return result;
	}

//	public List<String> showTables() {
//		return dao.showTables();
//	}
}
