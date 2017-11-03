package com.elong.nbapi.statistic.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.elong.nbapi.common.constants.Constant;
import com.elong.nbapi.common.po.Module;
import com.elong.nbapi.statistic.service.StatisticServiceImpl;

@Controller
@EnableScheduling
public class IndexController {

	@Autowired
	private StatisticServiceImpl service;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView gotoIndexPage(Model model) {
		model.addAttribute(Constant.HEADER_ACTIVE_ITEM, new String[] { "index" });
		model.addAttribute("sysModuleList", new ArrayList<Module>());
		model.addAttribute("businessList", service.getBusinessSystemList());

		String page = getCmmPageUrl("index");
		ModelAndView modelAndView = new ModelAndView(page);
		return modelAndView;
	}

	protected String getCmmPageUrl(String url) {
		return "/common/" + url;
	}

}
