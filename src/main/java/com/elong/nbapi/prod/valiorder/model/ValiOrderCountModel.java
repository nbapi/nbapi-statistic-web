package com.elong.nbapi.prod.valiorder.model;

public class ValiOrderCountModel {

	private String userName;
	
	private int valiType;
	
	private Long daySumCount;
	
	private Long successCount;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public int getValiType() {
		return valiType;
	}

	public void setValiType(int valiType) {
		this.valiType = valiType;
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
