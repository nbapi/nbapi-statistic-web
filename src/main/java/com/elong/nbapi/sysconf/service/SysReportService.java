/**   
 * @(#)SysReportService.java	2017年12月20日	下午2:24:51	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nbapi.sysconf.service;

import java.util.List;

import com.elong.nbapi.common.po.ReportSystem;

/**
 * (类型功能说明描述)
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年12月20日 下午2:24:51   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		zhangyang.zhu  
 * @version		1.0  
 * @since		JDK1.7
 */
public interface SysReportService {
	public List<ReportSystem> getReportSystem();
	public void addReportSystem(ReportSystem reportSystem);
	public void deleteReportSystemById(ReportSystem reportSystem);
	public void updateReportSystem(ReportSystem reportSystem);
}
