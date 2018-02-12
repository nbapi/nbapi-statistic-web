package com.elong.nbapi.prod.valiorder.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.elong.nb.UserServiceAgent;
import com.elong.nb.common.model.ProxyAccount;
import com.elong.nbapi.prod.valiorder.service.ValiOrderService;

@Controller
@RequestMapping(value = "/prod/valiorder")
public class ValiOrderController {

	@Resource
	private ValiOrderService service;

	@RequestMapping(value = "/valiOrderSummaryPage", method = { RequestMethod.GET })
	public ModelAndView logSummaryPage(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/prod/valiorder/valiOrderSummaryPage");
		return mav;
	}

	@RequestMapping(value = "/getSummaryData", method = { RequestMethod.GET })
	public @ResponseBody
	Map<String, Object> getSummaryData(HttpServletRequest request,
			HttpServletResponse response) {
		String ds = request.getParameter("ds");
		return service.queryAllValiOrder(ds);
	}
	
	@RequestMapping(value = "/valiOrderDetailPage", method = { RequestMethod.GET })
	public ModelAndView logDetailPage(HttpServletRequest request,
			HttpServletResponse response) {
		String ds = request.getParameter("ds");
		String username = request.getParameter("username");
		ModelAndView mav = new ModelAndView();
		mav.addObject("ds", ds);
		mav.addObject("username", username);
		ProxyAccount pa = UserServiceAgent.findProxyByUsername(username);
		mav.addObject("title", pa == null ? username : pa.getProjectName());
		mav.setViewName("/prod/valiorder/valiOrderDetailPage");
		return mav;
	}
	
	@RequestMapping(value = "/getDetailData", method = { RequestMethod.GET })
	public @ResponseBody
	Map<String, Object> getDetailData(HttpServletRequest request,
			HttpServletResponse response) {
		String ds = request.getParameter("ds");
		String username = request.getParameter("username");
		return service.queryAllError(ds, username);
	}
}
