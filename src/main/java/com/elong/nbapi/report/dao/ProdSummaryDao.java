package com.elong.nbapi.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.elong.nb.db.DataSource;


@DataSource("read_datasource")
public interface ProdSummaryDao {

	public <T> List<T> executSQL(@Param("ds") String ds,@Param("sql") String sql);
	
}
