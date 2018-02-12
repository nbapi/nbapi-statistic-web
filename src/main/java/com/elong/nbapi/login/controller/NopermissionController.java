/**   
 * Copyright © 2017 eLongNet Information Technology(Beijing) Co., Ltd.. All rights reserved.
 */
package com.elong.nbapi.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 没有权限的处理
 * 
 * <p>
 * 修改历史:											<br>
 * 修改日期    		修改人员   	版本	 		修改内容	<br>
 * -------------------------------------------------<br>
 * 2017.07.31   qianqian.xu     1.0			初始化创建<br>
 * </p> 
 * 
 * @author		qianqian.xu
 * @department	northbound
 */

@Controller
@RequestMapping("/nopermission")
public class NopermissionController {

    /**
     * 
     * 返回没有权限的页面
     * 
     * @param request
     * @param ressponse
     * @return
     */
    @RequestMapping(value="/index",method=RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request,HttpServletResponse ressponse){
        ModelAndView mv = new ModelAndView("nopermission/index");
        return mv;
    }
}
