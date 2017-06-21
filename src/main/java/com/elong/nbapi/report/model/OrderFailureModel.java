package com.elong.nbapi.report.model;

public class OrderFailureModel {

	private String proxyOrderFrom;
	
	private String failure;
	
	private int count;

	public String getProxyOrderFrom() {
		return proxyOrderFrom;
	}

	public void setProxyOrderFrom(String proxyOrderFrom) {
		this.proxyOrderFrom = proxyOrderFrom;
	}

	public String getFailure() {
		return failure;
	}

	public void setFailure(String failure) {
		this.failure = failure;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
