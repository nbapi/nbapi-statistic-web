/**   
 * @(#)UserCenterUtils.java	2017年12月21日	下午7:04:41	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nbapi.login.filter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.UrlUtils;

import com.alibaba.fastjson.JSONObject;
import com.elong.nbapi.common.utils.AppConfigUtil;
import com.elong.nbapi.common.utils.Http;

/**
 * 用户中心工具类
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年12月21日 下午7:04:41   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class UserCenterUtils {

	private static final Log logger = LogFactory.getLog(UserCenterUtils.class);

	public static final String aos_loginurl = AppConfigUtil.getCommonConfig("aos_loginurl");

	public static final String aos_ticket = AppConfigUtil.getCommonConfig("aos_ticket");

	public static final String aos_parseticket = AppConfigUtil.getCommonConfig("aos_parseticket");

	public static final String aos_dimension = AppConfigUtil.getCommonConfig("aos_dimension");

	public static final String aos_nopermissionurl = AppConfigUtil.getCommonConfig("aos_nopermissionurl");

	/** 
	 * 根据token和子系统名获取ticket
	 *
	 * @param token
	 * @return
	 */
	public static String getTicket(String token) {
		String result = Http.Send("POST", aos_ticket + "?subsystem=" + aos_dimension + "&token=" + token, "", "application/json");
		if (StringUtils.isEmpty(result))
			return null;
		// 调用成功，解析返回值
		JSONObject jsonObj = JSONObject.parseObject(result);
		if (jsonObj == null || jsonObj.getIntValue("code") != 200 || jsonObj.getJSONObject("data") == null)
			return null;
		// 解析用户信息
		JSONObject ret = jsonObj.getJSONObject("data");
		String ticket = ret.getString("ticket");
		logger.info("ticket = " + ticket + ",which is getTicket(" + token + "," + aos_dimension + ").");
		return ticket;
	}

	/** 
	 * 根据ticket获取用户信息
	 *
	 * @param ticket
	 * @return
	 */
	public static String getUsername(String ticket) {
		if (StringUtils.isEmpty(ticket))
			return null;
		// 根据ticket获取用户信息
		String result = Http.Send("POST",
				aos_parseticket + "?subsystem=" + aos_dimension + "&ticket=" + UrlUtils.urlEncode(ticket, "utf-8"), "", "application/json");
		logger.info("resut which is getUsername by ticket[" + ticket + "] = " + result);
		if (StringUtils.isEmpty(result))
			return null;
		// 调用成功，解析返回值
		JSONObject jsonObj = JSONObject.parseObject(result);
		if (jsonObj == null || jsonObj.getIntValue("code") != 200 || jsonObj.getJSONObject("data") == null)
			return null;
		// 解析用户信息
		JSONObject ret = jsonObj.getJSONObject("data");
		String username = ret.getString("username");
		logger.info("username = " + username + ",which is getUsername(" + ticket + ").");
		return username;
	}

	/** 
	 * 判断用户是否有访问地址权限 
	 *
	 * @param username
	 * @param perssion_path
	 * @return
	 */
	public static boolean hasPermission(String username, String perssion_path) {
		String retPermission = Http.Send("GET",
				AppConfigUtil.getCommonConfig("aos_permission") + "?username=" + AppConfigUtil.getCommonConfig(perssion_path)
						+ AppConfigUtil.getCommonConfig(perssion_path), "", "application/json");
		JSONObject resultPermission = JSONObject.parseObject(retPermission);
		if (resultPermission == null || resultPermission.getIntValue("code") != 200 || !resultPermission.getBooleanValue("data")) {
			logger.info("url: " + AppConfigUtil.getCommonConfig("aos_permission") + "?username=" + username
					+ AppConfigUtil.getCommonConfig(perssion_path));
			logger.info("用户 [" + username + "]无权限访问页面: " + resultPermission);
			return false;
		}
		return true;
	}

}
