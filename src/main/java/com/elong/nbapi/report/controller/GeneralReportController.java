package com.elong.nbapi.report.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.elong.nbapi.common.po.ReportSystem;
import com.elong.nbapi.report.service.GeneralReportService;

@Controller
@RequestMapping(value = "/report/generalReport")
public class GeneralReportController {

	@Resource
	private GeneralReportService service;
	
	@RequestMapping(value = "/rptPage", method = { RequestMethod.GET })
	public ModelAndView rptPage(ReportSystem reportSystem) {
		ReportSystem findOne=service.getReportSystem(reportSystem.getId());
		ModelMap modelMap=new ModelMap();
		modelMap.addAttribute("reportName", findOne.getDimensionName());
		ModelAndView mav=new ModelAndView("/report/mainReportPage", modelMap);
		return mav;
	}
	
	@RequestMapping(value = "/getRptData", method = { RequestMethod.GET })
	public @ResponseBody
	Map<String, Object> getRptData(HttpServletRequest request,
			HttpServletResponse response) {
		String ds = request.getParameter("ds");
		String id=request.getParameter("id");
		return service.queryReport(ds,id);
	}

}
