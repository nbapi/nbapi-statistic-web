/**   
 * @(#)LoginAndPermissionFilter.java	2017年12月21日	下午3:27:44	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nbapi.login.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 用户登录鉴权过滤器
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年12月21日 下午3:27:44   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class LoginAndPermissionFilter extends OncePerRequestFilter {

	/** 
	 * 用户登录鉴权
	 *
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws ServletException
	 * @throws IOException 
	 *
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)    
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 判断是否登录
		if (!isLogin(request, response)) {
			response.sendRedirect(UserCenterUtils.aos_loginurl + UserCenterUtils.aos_dimension);// 登录页面
		}
		// 判断是否有权限
		if (!hasPermission(request, response)) {
			response.sendRedirect(UserCenterUtils.aos_nopermissionurl);// 没权限页面
		}
		filterChain.doFilter(request, response);
	}

	/** 
	 * 判断用户是否登录 
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean isLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 验证是否存在会话
			HttpSession session = request.getSession();
			String username = (String) session.getAttribute("username");
			logger.info("username = " + username + ",which is from session.");
			if (StringUtils.isNotEmpty(username))
				return true;

			// 获取ticket，并解票
			String ticket = request.getParameter("ticket");
			username = UserCenterUtils.getUsername(ticket);
			logger.info("username = " + username + ",which is getUsername(" + ticket + ").");
			if (StringUtils.isNotEmpty(username)) {
				session.setAttribute("username", username);
				return true;
			}
			// 获取根域名下token
			Cookie[] cookies = request.getCookies();
			if (cookies == null || cookies.length == 0) {
				logger.error("cookie is null or has no element");
				return false;
			}
			String utoken = null;
			for (Cookie cookie : cookies) {
				if (!StringUtils.equals("utoken", cookie.getName()))
					continue;
				utoken = cookie.getValue();
			}
			if (StringUtils.isEmpty(utoken)) {
				logger.error("utoken doesn't exists!");
				return false;
			}
			// 根据token和子系统名获取ticket
			ticket = UserCenterUtils.getTicket(utoken);
			logger.info("ticket = " + ticket + ",which is getTicket(" + utoken + "," + UserCenterUtils.aos_dimension + ").");
			if (StringUtils.isEmpty(ticket)) {
				logger.error("ticket is valid!");
				return false;
			}
			// 解票
			username = UserCenterUtils.getUsername(ticket);
			logger.info("username = " + username + ",which is getUsername(" + ticket + ").");
			if (StringUtils.isEmpty(username)) {
				logger.error("decode ticket which get by token fail!");
				return false;
			}
			// 保存会话
			session.setAttribute("username", username);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	/** 
	 * 判断用户是否有权限
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean hasPermission(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String[] paths = { "nbcheck", "nbincrinsert", "nbbalance", "nbincrquery", "nborder", "nbpay", "nbuser", "nbdata", "daily",
				"logentity", "sysconf", "report", "prod" };
		for (String path : paths) {
			if (request.getServletPath().contains(path)) {
				return UserCenterUtils.hasPermission(username, "aos_permission_" + path);
			}
		}
		return true;
	}

}
