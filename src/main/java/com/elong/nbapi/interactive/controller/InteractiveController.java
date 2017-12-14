package com.elong.nbapi.interactive.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.elong.nbapi.interactive.service.InteractiveService;

@Controller
@RequestMapping(value = "/ia")
public class InteractiveController {

	@Autowired
	private InteractiveService service;

	@RequestMapping(value = "/interactivePage", method = { RequestMethod.GET })
	public ModelAndView logSummaryPage(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/interactive/mainPage");
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("tables", service.showTables());
		mav.addAllObjects(modelMap);
		return mav;
	}

	@RequestMapping(value = "/exesql", method = { RequestMethod.GET })
	public @ResponseBody
	Map<String, Object> exesql(HttpServletRequest request,
			HttpServletResponse response) {
		String sql = request.getParameter("sql");
		Map<String, Object> rst = null;
		try{
			rst = service.exeSQL(sql);
		}catch(Exception e){
			rst = new HashMap<String, Object>();
			rst.put("error", e.getMessage());
		}
		return rst;
	}

}
