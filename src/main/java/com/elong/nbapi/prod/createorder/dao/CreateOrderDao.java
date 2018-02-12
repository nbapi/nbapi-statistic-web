package com.elong.nbapi.prod.createorder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.elong.nb.db.DataSource;
import com.elong.nbapi.prod.createorder.model.CreateOrderCountModel;
import com.elong.nbapi.prod.createorder.model.CreateOrderErrorModel;

@DataSource("read_datasource")
public interface CreateOrderDao {

	public List<CreateOrderCountModel> queryAllCreateOrder(@Param("ds") String ds);
	
	public List<CreateOrderErrorModel> queryAllError(@Param("ds") String ds, @Param("username") String username);
	
}
