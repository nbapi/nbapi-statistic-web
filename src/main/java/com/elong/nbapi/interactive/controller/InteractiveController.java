package com.elong.nbapi.interactive.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.elong.nbapi.interactive.service.InteractiveService;

@Controller
public class InteractiveController {

	@Autowired
	private InteractiveService service;

	/**
	 * 
	 * 获取每分钟流量
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getDwAllMinute(HttpServletRequest request)
			throws IOException {
		return new ResponseEntity<byte[]>(service.test().getBytes(),
				HttpStatus.OK);
	}
}
