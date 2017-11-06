/**   
 * Copyright © 2017 eLongNet Information Technology(Beijing) Co., Ltd.. All rights reserved.
 */
package com.elong.nbapi.login.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.common.i18n.Exception;
import org.apache.cxf.common.util.UrlUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.elong.nbapi.common.utils.AppConfigUtil;
import com.elong.nbapi.common.utils.Http;
import com.elong.nbapi.login.model.AosTokenInfo;

/**
 * (类型功能说明描述) TODO
 * 
 * <p>
 * 修改历史:											<br>
 * 修改日期    		修改人员   	版本	 		修改内容	<br>
 * -------------------------------------------------<br>
 * 2017.10.23   qianqian.xu     1.0			初始化创建<br>
 * </p> 
 * 
 * @author		qianqian.xu
 * @department	northbound
 */
public class UserLoginInterceptor extends HandlerInterceptorAdapter {

	private Logger log = Logger.getLogger(this.getClass());

	/** 
	 * 拦截以作登录校验
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)    
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

		// 重置session超时时间
		SessionHelper.resetSessionExpireTime();

		// 判断用户是否登录
		if (StringUtils.isNotEmpty(SessionHelper.getUserName())) {
			log.info("用户 [" + SessionHelper.getUserName() + "]已登录");
			// 校验用户页面访问权限
			if (!checkInnerUserPermission(request)) {
				// 去往无权限页面
				response.sendRedirect(AppConfigUtil.getCommonConfig("aos-nopermissionurl"));
			}
			return true;
		}

		// 如果没有登录，那么检查是否有ticket(内部用户登录凭证)
		if (request.getParameter("ticket") != null) {

			if (!getInnerUserInfo(request)) {
				// 调用失败，去aos登录页面
				response.sendRedirect(AppConfigUtil.getCommonConfig("aos-loginurl") + AppConfigUtil.getCommonConfig("aos-dimension"));
			} else {
				// 校验用户页面访问权限
				if (!checkInnerUserPermission(request)) {
					// 去往无权限页面
					response.sendRedirect(AppConfigUtil.getCommonConfig("aos-nopermissionurl"));
				}
			}

			return true;
		}

		response.sendRedirect(AppConfigUtil.getCommonConfig("aos-loginurl") + AppConfigUtil.getCommonConfig("aos-dimension"));
		return true;
	}

	private boolean checkInnerUserPermission(HttpServletRequest request) {
		String[] paths = { "nbcheck", "nbincrinsert", "nbbalance", "nbincrquery", "nborder", "nbpay", "nbuser", "nbdata", "daily",
				"logentity", "sysconf", "report" };
		for (String path : paths) {
			if (request.getServletPath().contains(path)) {
				return checkPermission("aos-permission-" + path);
			}
		}
		return true;
	}

	private boolean checkPermission(String perssion_path) {
		String retPermission = Http.Send("GET", AppConfigUtil.getCommonConfig("aos-permission") + "?username="
				+ SessionHelper.getUserBean().getUserName() + AppConfigUtil.getCommonConfig(perssion_path), "", "application/json");
		JSONObject resultPermission = JSONObject.parseObject(retPermission);
		if (resultPermission == null || resultPermission.getIntValue("code") != 200 || !resultPermission.getBooleanValue("data")) {
			log.info("url: " + AppConfigUtil.getCommonConfig("aos-permission") + "?username=" + SessionHelper.getUserBean().getUserName()
					+ AppConfigUtil.getCommonConfig(perssion_path));
			log.info("用户 [" + SessionHelper.getUserName() + "]无权限访问页面: " + resultPermission);
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 内部用户信息解析与保存
	 * 
	 * @param request
	 * @return
	 */
	private boolean getInnerUserInfo(HttpServletRequest request) {
		String ticket = request.getParameter("ticket");
		// 根据ticket获取用户信息
		String responseUserInfo = Http.Send("POST",
				AppConfigUtil.getCommonConfig("aos-parseticket") + "?subsystem=" + AppConfigUtil.getCommonConfig("aos-dimension")
						+ "&ticket=" + UrlUtils.urlEncode(ticket, "utf-8"), "", "application/json");
		log.info("用户登录校验返回： " + responseUserInfo);
		if (StringUtils.isBlank(responseUserInfo)) {
			// 调用失败，去aos登录页面
			return false;
		} else {
			// 调用成功，解析返回值
			JSONObject jsonUser = JSONObject.parseObject(responseUserInfo);
			if (jsonUser != null && jsonUser.getIntValue("code") == 200 && jsonUser.getJSONObject("data") != null) {
				// 解析用户信息并存入session
				JSONObject ret = jsonUser.getJSONObject("data");
				AosTokenInfo aosTokenInfo = new AosTokenInfo();
				if (SessionHelper.getUserBean() == null || SessionHelper.getUserBean().getUserName() == null
						|| !SessionHelper.getUserBean().getUserName().equals(ret.getString("username"))) {
					SessionHelper.setUserName(ret.getString("username"));
					aosTokenInfo.setId(ret.getString("id"));
					aosTokenInfo.setEmail(ret.getString("email"));
					aosTokenInfo.setExt(ret.getString("ext"));
					aosTokenInfo.setPhone(ret.getString("phone"));
					aosTokenInfo.setRealName(ret.getString("realname"));
					aosTokenInfo.setStatus(ret.getString("status"));
					aosTokenInfo.setUserName(ret.getString("username"));
					aosTokenInfo.setUserType(ret.getString("userType"));
					SessionHelper.setUserBean(aosTokenInfo);
				}
			} else {
				// 登录失败，去往aos登录页面
				return false;
			}
		}
		return true;
	}
}
