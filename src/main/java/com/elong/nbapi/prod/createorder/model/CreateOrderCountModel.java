package com.elong.nbapi.prod.createorder.model;

public class CreateOrderCountModel {

	private String userName;
	
	private Long daySumCount;
	
	private Long successCount;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public Long getDaySumCount() {
		return daySumCount;
	}

	public void setDaySumCount(Long daySumCount) {
		this.daySumCount = daySumCount;
	}

	public Long getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Long successCount) {
		this.successCount = successCount;
	}
	
}
