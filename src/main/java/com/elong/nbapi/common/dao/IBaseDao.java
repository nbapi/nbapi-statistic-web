package com.elong.nbapi.common.dao;

import java.util.List;

import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public interface IBaseDao {

	public <T> List<T> find(Query query, Class<T> entityClass);

	public <T> List<T> find(Query query, Class<T> entityClass,
			String collectionName);

	public <T> List<T> findAll(Class<T> entityClass);

	public void insert(Object objectToSave);

	public void save(Object objectToSave);

	public <T> T findOne(Query query, Class<T> entityClass);

	public <T> void updateOne(Query query, Update update, Class<T> entityClass);

	public <T> void deleteOne(Query query, Class<T> entityClass);

	public void executeQuery(Query query, String collectionName,
			DocumentCallbackHandler dch);
	
	public <T> void remove(Query query, Class<T> entityClass);
	
	public void remove(Query query, String collectionName);
	
    public void remove(Object object);


}
