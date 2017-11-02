package com.elong.nbapi.login.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    
    /**
     * 外部用户信息映射
     */
    private final static String sessionOutTokenBeanMap = "session_out_info_map";

    /**
     * 内部aos用户名
     */
    private final static String sessionUserAttr = "session_user_name";

    /**
     * 用户aos token信息
     */
    private final static String aostokenbean = "session_aos_tokenbean";

    /**
     * 获取用户登录名
     *
     * @return
     */
    public static String getUserName() {
        String uname = "";
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if (session.getAttribute(sessionUserAttr) != null) {
            uname = String.valueOf(session.getAttribute(sessionUserAttr));
        }
        ;
        return uname;
    }

    /**
     * 设置用户session
     *
     * @param userName
     */
    public static void setUserName(String userName) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(sessionUserAttr, userName);
    }

    /**
     * 设置用户aos token信息
     *
     * @param aosTokenInfo
     */
    public static void setUserBean(AosTokenInfo aosTokenInfo){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(aostokenbean,aosTokenInfo);
    }

    /**
     * 获取用户aos token信息
     *
     * @return
     */
    public static AosTokenInfo getUserBean(){
        AosTokenInfo aosTokenInfo = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if(session.getAttribute(aostokenbean)!=null){
            aosTokenInfo=(AosTokenInfo)session.getAttribute(aostokenbean);
        }
        return aosTokenInfo;
    }
    
    /**
     * 用户退出
     */
    public static void removeSessionUserInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        session.removeAttribute(sessionUserAttr);
        session.removeAttribute(aostokenbean);
    }

}

