package com.elong.nbapi.prod.logentity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.elong.nb.db.DataSource;
import com.elong.nbapi.prod.logentity.model.DayLogEntityModel;
import com.elong.nbapi.prod.logentity.model.MinuteLogEntityModel;
import com.elong.nbapi.prod.logentity.model.SummaryLogEntityModel;

@DataSource("read_datasource")
public interface LogEntityDao {

	public List<SummaryLogEntityModel> queryAllSummary(
			@Param("startDs") String startDs, @Param("endDs") String endDs);

	public List<DayLogEntityModel> queryAllDay(@Param("ds") String ds);
	
	public List<DayLogEntityModel> queryAllDayByUsername(@Param("ds") String ds, @Param("username") String username);
	
	public List<MinuteLogEntityModel> queryAllMinute(@Param("ds") String ds, @Param("username") String username);

}
