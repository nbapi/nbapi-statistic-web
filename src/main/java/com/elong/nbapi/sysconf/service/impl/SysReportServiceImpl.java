/**   
 * @(#)SysReportServiceImpl.java	2017年12月20日	下午2:25:08	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nbapi.sysconf.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.elong.nbapi.common.dao.IBaseDao;
import com.elong.nbapi.common.po.BusinessSystem;
import com.elong.nbapi.common.po.ReportSystem;
import com.elong.nbapi.sysconf.service.SysReportService;

/**
 * (类型功能说明描述)
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年12月20日 下午2:25:08   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		zhangyang.zhu  
 * @version		1.0  
 * @since		JDK1.7
 */
@Service
public class SysReportServiceImpl implements SysReportService {
	
	@Resource
	private IBaseDao baseDao;

	@Override
	public List<ReportSystem> getReportSystems() {
		return baseDao.findAll(ReportSystem.class);
	}
	
	@Override
	public void addReportSystem(ReportSystem reportSystem) {
		 baseDao.save(reportSystem);
	}

	@Override
	public void deleteReportSystemById(ReportSystem reportSystem) {
		baseDao.remove(reportSystem);
	}

	@Override
	public void updateReportSystem(ReportSystem reportSystem) {
		Criteria criteria=Criteria.where(ReportSystem.FIELD_ID).is(reportSystem.getId());
		ReportSystem findOne=baseDao.findOne(Query.query(criteria), ReportSystem.class);
		
		if(StringUtils.isNotEmpty(reportSystem.getDimensionName())){
			findOne.setDimensionName(reportSystem.getDimensionName());
		}
		
		if(StringUtils.isNotEmpty(reportSystem.getLocalName())){
			findOne.setLocalName(reportSystem.getLocalName());
		}
		if(StringUtils.isNotEmpty(reportSystem.getReportSQL())){
			findOne.setReportSQL(reportSystem.getReportSQL());
		}
		if(StringUtils.isNotEmpty(reportSystem.getReportCols())){
			findOne.setReportCols(reportSystem.getReportCols());
		}
		if(StringUtils.isNotEmpty(reportSystem.getAliasNames())){
			findOne.setAliasNames(reportSystem.getAliasNames());
		}
		baseDao.save(findOne);
	}

	/** 
	 * (方法说明描述) 
	 *
	 * @return 
	 *
	 * @see com.elong.nbapi.sysconf.service.SysReportService#getReportSystem()    
	 */
	@Override
	public ReportSystem getReportSystem(String id) {
	        Criteria criteria = Criteria.where(BusinessSystem.FIELD_ID).is(id);
	        Query query = Query.query(criteria);
	        return baseDao.findOne(query, ReportSystem.class);
	}
	
	
	
}
