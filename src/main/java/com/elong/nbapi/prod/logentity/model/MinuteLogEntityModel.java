package com.elong.nbapi.prod.logentity.model;

public class MinuteLogEntityModel {

	private String methodName;
	
	private String sminute;
	
	private long minuteSumCount;
	
	private long minuteSumExeTime;
	
	private long minuteSumErrorCount;
	
	private String proxyId;
	
	private String ds;

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getSminute() {
		return sminute;
	}

	public void setSminute(String minute) {
		this.sminute = minute;
	}

	public long getMinuteSumCount() {
		return minuteSumCount;
	}

	public void setMinuteSumCount(long minuteSumCount) {
		this.minuteSumCount = minuteSumCount;
	}

	public long getMinuteSumExeTime() {
		return minuteSumExeTime;
	}

	public void setMinuteSumExeTime(long minuteSumExeTime) {
		this.minuteSumExeTime = minuteSumExeTime;
	}

	public long getMinuteSumErrorCount() {
		return minuteSumErrorCount;
	}

	public void setMinuteSumErrorCount(long minuteSumErrorCount) {
		this.minuteSumErrorCount = minuteSumErrorCount;
	}

	public String getProxyId() {
		return proxyId;
	}

	public void setProxyId(String proxyId) {
		this.proxyId = proxyId;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}
	
	
}
