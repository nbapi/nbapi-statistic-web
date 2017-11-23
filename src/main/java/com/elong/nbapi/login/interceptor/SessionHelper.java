package com.elong.nbapi.login.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.elong.nb.cache.RedisManager;
import com.elong.nbapi.login.model.AosTokenInfo;

/**
 * 用于对Aos用户的Session的各项操作
 * <p/>
 * <p>
 * 修改历史:											<br>
 * 修改日期    		修改人员   	版本	 		修改内容	<br>
 * -------------------------------------------------<br>
 * 2017/7/27   qianqian.xu     1.0			初始化创建<br>
 * </p>
 *
 * @author qianqian.xu
 * @department northbound
 */
public class SessionHelper {

	private static RedisManager redisManager = RedisManager.getInstance("redis_shared", "redis_shared");

	/**
	 * 内部aos用户名
	 */
	private final static String sessionUserAttr = "session_user_name";

	/**
	 * 用户aos token信息
	 */
	private final static String aostokenbean = "session_aos_tokenbean";

	/** 
	 * session过期时长9小时
	 *
	 * int SessionHelper.java sessionExpireSeconds
	 */
	private static int sessionExpireSeconds = 9 * 60 * 60;

	/**
	 * 获取用户登录名
	 *
	 * @return
	 */
	public static String getUserName() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		String redisSessionKey = sessionUserAttr + "_" + session.getId();
		return redisManager.get(RedisManager.getCacheKey(redisSessionKey));
	}

	/**
	 * 设置用户session
	 *
	 * @param userName
	 */
	public static void setUserName(String userName) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		String redisSessionKey = sessionUserAttr + "_" + session.getId();
		redisManager.put(RedisManager.getCacheKey(redisSessionKey, sessionExpireSeconds), userName);
	}

	/**
	 * 设置用户aos token信息
	 *
	 * @param aosTokenInfo
	 */
	public static void setUserBean(AosTokenInfo aosTokenInfo) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		String redisSessionKey = aostokenbean + "_" + session.getId();
		redisManager.put(RedisManager.getCacheKey(redisSessionKey, sessionExpireSeconds), aosTokenInfo);
	}

	/**
	 * 获取用户aos token信息
	 *
	 * @return
	 */
	public static AosTokenInfo getUserBean() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		String redisSessionKey = aostokenbean + "_" + session.getId();
		String resultStr = redisManager.get(RedisManager.getCacheKey(redisSessionKey));
		return JSON.parseObject(resultStr, AosTokenInfo.class);
	}
	
	public static void resetSessionExpireTime(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		String userKey = sessionUserAttr + "_" + session.getId();
		redisManager.expire(RedisManager.getCacheKey(userKey));
		String tokenKey = aostokenbean + "_" + session.getId();
		redisManager.expire(RedisManager.getCacheKey(tokenKey));
	}

	/**
	 * 用户退出
	 */
	public static void removeSessionUserInfo() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		String userKey = sessionUserAttr + "_" + session.getId();
		redisManager.del(RedisManager.getCacheKey(userKey));
		String tokenKey = aostokenbean + "_" + session.getId();
		redisManager.del(RedisManager.getCacheKey(tokenKey));
	}

}
