package com.elong.nbapi.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.elong.nb.db.DataSource;
import com.elong.nbapi.report.model.ProdSummary;


@DataSource("read_datasource")
public interface ProdSummaryDao {

	public List<ProdSummary> queryAllProdSummary_q(@Param("ds") String ds);
	
	public List<ProdSummary> queryAllProdSummary_c(@Param("ds") String ds);
	
}
