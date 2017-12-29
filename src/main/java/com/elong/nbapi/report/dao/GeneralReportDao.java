package com.elong.nbapi.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.elong.nb.db.DataSource;


@DataSource("read_datasource")
public interface GeneralReportDao {

	public <T> List<T> selectReport(@Param("ds") String ds,@Param("tableName") String tableName);
	
}
