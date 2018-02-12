package com.elong.nbapi.prod.valiorder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.elong.nb.db.DataSource;
import com.elong.nbapi.prod.valiorder.model.ValiOrderCountModel;
import com.elong.nbapi.prod.valiorder.model.ValiOrderErrorModel;

@DataSource("read_datasource")
public interface ValiOrderDao {

	public List<ValiOrderCountModel> queryAllValiOrder(@Param("ds") String ds);
	
	public List<ValiOrderErrorModel> queryAllError(@Param("ds") String ds, @Param("username") String username);
	
}
