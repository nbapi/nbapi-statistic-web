package com.elong.nbapi.prod.createorder.model;

public class CreateOrderErrorModel {

	private String respCode;
	
	private Long daySumCount;

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public Long getDaySumCount() {
		return daySumCount;
	}

	public void setDaySumCount(Long daySumCount) {
		this.daySumCount = daySumCount;
	}

}
