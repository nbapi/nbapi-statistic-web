package com.elong.nbapi.prod.logentity.controller;

import java.text.SimpleDateFormat;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.elong.nbapi.prod.logentity.service.LogEntityService;

@Controller
@RequestMapping(value = "/prod/logentity")
public class LogEntityController {

	private final long dateRange = 14 * 24 * 60 * 60 * 1000l;	//15天
	
	@Resource
	private LogEntityService service;

	@RequestMapping(value = "/logSummaryPage", method = { RequestMethod.GET })
	public ModelAndView logSummaryPage(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/prod/logentity/logSummaryPage");
		return mav;
	}

	@RequestMapping(value = "/getSummaryData", method = { RequestMethod.GET })
	public @ResponseBody
	Map<String, Object> getSummaryData(HttpServletRequest request,
			HttpServletResponse response) {
		String startDs = request.getParameter("startDs");
		String endDs = request.getParameter("endDs");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try{
			long start = sdf.parse(startDs).getTime();
			long end = sdf.parse(endDs).getTime();
			if (start > end || end - start > dateRange) throw new RuntimeException("out of date range!");
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("date error!");
		}
		return service.queryAllSummary(startDs, endDs);
	}

	@RequestMapping(value = "/logMainPage", method = { RequestMethod.GET })
	public ModelAndView logMainPage(HttpServletRequest request,
			HttpServletResponse response) {
		String ds = request.getParameter("ds");
		ModelAndView mav = new ModelAndView();
		mav.addObject("ds", ds);
		mav.setViewName("/prod/logentity/logMainPage");
		return mav;
	}
	
	@RequestMapping(value = "/getMainData", method = { RequestMethod.GET })
	public @ResponseBody
	Map<String, Object> getMainData(HttpServletRequest request,
			HttpServletResponse response) {
		String ds = request.getParameter("ds");
		return service.queryAllDay(ds);
	}
}
