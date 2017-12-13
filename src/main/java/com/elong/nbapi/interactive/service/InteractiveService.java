package com.elong.nbapi.interactive.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elong.nbapi.interactive.dao.PrestoDao;

@Service
public class InteractiveService {

	@Resource
	private PrestoDao dao;
	
	public String test(){
		return dao.findMethodCount("20171204").get(0);
	}
}
