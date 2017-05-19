package com.elong.nbapi.common.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.elong.nbapi.common.dao.IBaseDao;

@Repository
public class BaseDao implements IBaseDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public <T> List<T> find(Query query, Class<T> entityClass) {
		return mongoTemplate.find(query, entityClass);
	}

	@Override
	public <T> List<T> find(Query query, Class<T> entityClass,
			String collectionName) {
		return mongoTemplate.find(query, entityClass, collectionName);
	}

	public <T> List<T> findAll(Class<T> entityClass) {
		return mongoTemplate.findAll(entityClass);
	}

	public void insert(Object objectToSave) {
		mongoTemplate.insert(objectToSave);
	}

	public void save(Object objectToSave) {
		mongoTemplate.save(objectToSave);
	}

	public <T> T findOne(Query query, Class<T> entityClass) {
		return mongoTemplate.findOne(query, entityClass);
	}

	public <T> void updateOne(Query query, Update update, Class<T> entityClass) {
		mongoTemplate.updateFirst(query, update, entityClass);

	}

	public <T> void deleteOne(Query query, Class<T> entityClass) {

		mongoTemplate.remove(query, entityClass);
	}

	public void executeQuery(Query query, String collectionName,
			DocumentCallbackHandler dch) {

		mongoTemplate.executeQuery(query, collectionName, dch);
	}
	
	public <T> void remove(Query query, Class<T> entityClass) {

		mongoTemplate.remove(query, entityClass);
	}
	
	public void remove(Query query, String collectionName) {
	    mongoTemplate.remove(query, collectionName);
	}

	public void remove(Object object) {
        mongoTemplate.remove(object);
    }

}
