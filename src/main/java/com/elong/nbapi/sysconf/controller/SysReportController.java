package com.elong.nbapi.sysconf.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.elong.nbapi.common.po.BusinessSystem;
import com.elong.nbapi.common.po.Dimension;
import com.elong.nbapi.common.po.DimensionMetricRelation;
import com.elong.nbapi.common.po.Metric;
import com.elong.nbapi.common.po.Module;
import com.elong.nbapi.common.po.ReportSystem;
import com.elong.nbapi.sysconf.service.SysConfService;
import com.elong.nbapi.sysconf.service.SysReportService;

@Controller
@RequestMapping("/sysconf")
public class SysReportController {
	@Resource
	private SysReportService sysReportService;
	
	 private String getPageUrl(String pageName) {
	        return "/sysconf/" + pageName;
	    }
	 
	@RequestMapping(value="/reportlist",method=RequestMethod.GET)
	public ModelAndView getReportList(){
		ModelMap modelMap=new ModelMap();
		modelMap.addAttribute("reportList", sysReportService.getReportSystems());
		String pageName=getPageUrl("reportlist");
		ModelAndView modelAndView=new ModelAndView(pageName,modelMap);
		return modelAndView;
	}
	@RequestMapping(value = "/addreport", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addReport(ReportSystem reportSystem) {
		sysReportService.addReportSystem(reportSystem);
        return new HashMap<String, Object>();
    }
	
	@RequestMapping(value="/updatereport",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>updateReportSystem(ReportSystem reportSystem){
		sysReportService.updateReportSystem(reportSystem);
		return new HashMap<String,Object>();
	}
	
	@RequestMapping(value="/deletereport",method=RequestMethod.POST)
	@ResponseBody
	public Map<String , Object>deleteReportSystem(ReportSystem reportSystem){
		sysReportService.deleteReportSystemById(reportSystem);
		return new HashMap<String,Object>();
	}
	
	@RequestMapping(value="/reportLeftNav",method=RequestMethod.GET)
	@ResponseBody
	public String reportLeftNav(){
		//return "success";
		return JSONObject.toJSONString(sysReportService.getReportSystems());
	}
}
