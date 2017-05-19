package com.elong.nbapi.sysconf.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.elong.nbapi.common.po.BusinessSystem;
import com.elong.nbapi.common.po.Dimension;
import com.elong.nbapi.common.po.DimensionMetricRelation;
import com.elong.nbapi.common.po.Metric;
import com.elong.nbapi.common.po.Module;
import com.elong.nbapi.sysconf.service.SysConfService;

@Controller
@RequestMapping("/sysconf")
public class SysConfController {

    @Autowired
    private SysConfService sysConfService;

    private String getPageUrl(String pageName) {
        return "/sysconf/" + pageName;
    }

    @RequestMapping(value = "/systemlist", method = RequestMethod.GET)
    public ModelAndView gotoSystemListPage() {

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("systemList", sysConfService.getSystemList());

        String page = getPageUrl("systemlist");
        ModelAndView modelAndView = new ModelAndView(page, modelMap);
        return modelAndView;
    }
    
    @RequestMapping(value = "/delsystem", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteSystemById(BusinessSystem system) {
        sysConfService.deleteSystemById(system);
        return new HashMap<String, Object>();
    }

    @RequestMapping(value = "/addsystem", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addSystem(BusinessSystem system) {
        sysConfService.addSystem(system);
        return new HashMap<String, Object>();
    }
    
    @RequestMapping(value = "/modulelist", method = RequestMethod.GET)
    public ModelAndView getModuleListBySystemId(BusinessSystem system) {
        
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("moduleList", sysConfService.getModuleListBySystemId(system));
        modelMap.addAttribute("system", sysConfService.getBusinessSystemById(system));

        String page = getPageUrl("modulelist");
        ModelAndView modelAndView = new ModelAndView(page, modelMap);
        
        return modelAndView;
    }
    
    @RequestMapping(value = "/addmodule", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addModule(Module module) {
        sysConfService.addModule(module);
        return new HashMap<String, Object>();
    }
    
    @RequestMapping(value = "/dimensionlist", method = RequestMethod.GET)
    public ModelAndView getDimensionListByModuleId(Module module) {
        
        ModelMap modelMap = new ModelMap();
        Module dbModule = sysConfService.getModuleByModuleId(module);
        modelMap.addAttribute("module", dbModule);
        BusinessSystem system = new BusinessSystem();
        system.setId(dbModule.getSystemId());
        modelMap.addAttribute("system", sysConfService.getBusinessSystemById(system));
        modelMap.addAttribute("dimensionList", sysConfService.getDimensionListByModuleId(module));

        String page = getPageUrl("dimensionlist");
        ModelAndView modelAndView = new ModelAndView(page, modelMap);
        
        return modelAndView;
    }
    
    
    @RequestMapping(value = "/adddimension", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addDimension(Dimension dimension) {
        sysConfService.addDimension(dimension);
        return new HashMap<String, Object>();
    }
    
    @RequestMapping(value = "/deldimension", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteDimensionByDimensionId(Dimension dimension) {
        sysConfService.deleteDimensionByDimensionId(dimension);
        return new HashMap<String, Object>();
    }
    
    @RequestMapping(value = "/delmodule", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteModuleByModuleId(Module module) {
        sysConfService.deleteModuleByModuleId(module);
        return new HashMap<String, Object>();
    }

    //
    @RequestMapping(value = "metriclist")
    public String gotoMetricPage(Model model) {
        List<Metric> metricList = sysConfService.findMetricList();
        model.addAttribute("metricList", metricList);

        String pageurl = getPageUrl("metric");
        return pageurl;
    }

    @RequestMapping(value = "delmetric", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteMetricById(Metric metric) {
        sysConfService.deleteMetricById(metric);

        return new HashMap<String, Object>();
    }

    @RequestMapping(value = "addmetric", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addMetric(Metric metric) {
        sysConfService.addMetric(metric);

        return new HashMap<String, Object>();
    }
    
    @RequestMapping(value = "updatemetric", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDisplayName(Metric metric) {
        sysConfService.updateMetric(metric);

        return new HashMap<String, Object>();
    }
    
    @RequestMapping(value = "getdimenmetricrelation", method = RequestMethod.POST)
    @ResponseBody
    public List<DimensionMetricRelation> getDimensionMetricRelations(
            DimensionMetricRelation relation) {
        
        List<DimensionMetricRelation> dimensionMetricRelations = sysConfService
                .getDimensionMetricRelations(relation);

        return dimensionMetricRelations;
    }
    
    @RequestMapping(value = "getAllMetrics", method = RequestMethod.POST)
    @ResponseBody
    public List<Metric> getAllMetrics() {
        List<Metric> metricList = sysConfService.findMetricList();

        return metricList;
    }
    
    @RequestMapping(value = "updaterelation", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDimensionMetriRelation(DimensionMetricRelation relation) {
        sysConfService.updateDimensionMetriRelation(relation);
        return new HashMap<String, Object>();
    }
    
    @RequestMapping(value = "updatesys", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateBussinessSystem(BusinessSystem system) {
        sysConfService.updateBussinessSystem(system);
        return new HashMap<String, Object>();
    }
    
    @RequestMapping(value = "updatemodule", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateModule(Module module) {
        sysConfService.updateModule(module);
        return new HashMap<String, Object>();
    }
    
    @RequestMapping(value = "updatedimension", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDimension(Dimension dimension) {
        sysConfService.updateDimension(dimension);
        return new HashMap<String, Object>();
    }
}
