package com.elong.nbapi.report.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.elong.nbapi.report.service.CheckFailureCountServiceImpl;
import com.elong.nbapi.report.service.MethodCountServiceImpl;
import com.elong.nbapi.report.service.OrderCountServiceImpl;
import com.elong.nbapi.report.service.OrderFailureCountServiceImpl;

@Controller
@RequestMapping(value = "/daily")
public class DailyController {

	@Resource
	private MethodCountServiceImpl dservice;
	
	@Resource
	private OrderCountServiceImpl oservice;
	
	@Resource
	private CheckFailureCountServiceImpl cservice;
	
	@Resource
	private OrderFailureCountServiceImpl fservice;
	
	@RequestMapping(value = "/methodcount", method = { RequestMethod.GET })
	public ModelAndView methodcount(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView(); 
		mav.setViewName("/report/methodcount_daily");

		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("selDate", dservice.getResultDate());
		
		mav.addAllObjects(modelMap);
		return mav;
	}
	
	@RequestMapping(value = "/methodcountdata", method = { RequestMethod.GET })
	public @ResponseBody Map<String, Object> getAllProxyCountData(HttpServletRequest request,
			HttpServletResponse response) {
		String countdate = request.getParameter("countdate");
		if (StringUtils.isBlank(countdate)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date yesterday = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
			countdate = sdf.format(yesterday);
		}

		List<String[]> data = dservice.getAllProxyCountData(countdate);

		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("title", data.get(0));
		data.remove(0);
		modelMap.put("data", data);

		return modelMap;
	}
	
	
	//-------------------------------------------------------------------------
	
	
	@RequestMapping(value = "/ordercount", method = { RequestMethod.GET })
	public ModelAndView ordercount(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView(); 
		mav.setViewName("/report/ordercount_daily");

		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("oselDate", oservice.getResultDate());
		
		mav.addAllObjects(modelMap);
		return mav;
	}

	@RequestMapping(value = "/ordercountdata", method = { RequestMethod.GET })
	public @ResponseBody Map<String, Object> getAllProxyOrderCountData(HttpServletRequest request,
			HttpServletResponse response) {
		String countdate = request.getParameter("countdate");
		if (StringUtils.isBlank(countdate)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date yesterday = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
			countdate = sdf.format(yesterday);
		}

		List<String[]> data = oservice.getAllProxyCountData(countdate);

		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("title", data.get(0));
		data.remove(0);
		modelMap.put("data", data);

		return modelMap;
	}
	
	//-------------------------------------------------------------------------
	
	@RequestMapping(value = "/checkfailurecount", method = { RequestMethod.GET })
	public ModelAndView checkfailurecount(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView(); 
		mav.setViewName("/report/checkfailurecount_daily");

		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("selDate", cservice.getResultDate());
		
		mav.addAllObjects(modelMap);
		return mav;
	}

	@RequestMapping(value = "/checkfailurecountdata", method = { RequestMethod.GET })
	public @ResponseBody Map<String, Object> getAllProxyCheckFailureCountData(HttpServletRequest request,
			HttpServletResponse response) {
		String countdate = request.getParameter("countdate");
		if (StringUtils.isBlank(countdate)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date yesterday = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
			countdate = sdf.format(yesterday);
		}

		Map<String, List<String[]>> data = cservice.getAllProxyCountData(countdate);

		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("data", data);

		return modelMap;
	}

	//-------------------------------------------------------------------------
	
		@RequestMapping(value = "/orderfailurecount", method = { RequestMethod.GET })
		public ModelAndView orderfailurecount(HttpServletRequest request,
				HttpServletResponse response) {
			ModelAndView mav = new ModelAndView(); 
			mav.setViewName("/report/orderfailurecount_daily");

			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("selDate", fservice.getResultDate());
			
			mav.addAllObjects(modelMap);
			return mav;
		}

		@RequestMapping(value = "/orderfailurecountdata", method = { RequestMethod.GET })
		public @ResponseBody Map<String, Object> getAllProxyOrderFailureCountData(HttpServletRequest request,
				HttpServletResponse response) {
			String countdate = request.getParameter("countdate");
			if (StringUtils.isBlank(countdate)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Date yesterday = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
				countdate = sdf.format(yesterday);
			}

			Map<String, List<String[]>> data = fservice.getAllProxyCountData(countdate);

			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("data", data);

			return modelMap;
		}

}