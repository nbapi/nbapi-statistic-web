package com.elong.nbapi.prod.logentity.model;

public class DayLogEntityModel {

	private String methodName;
	
	private long daySumCount;
	
	private long daySumExeTime;
	
	private long daySumErrorCount;
	
	private String proxyId;
	
	private String ds;

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public long getDaySumCount() {
		return daySumCount;
	}

	public void setDaySumCount(long daySumCount) {
		this.daySumCount = daySumCount;
	}

	public long getDaySumExeTime() {
		return daySumExeTime;
	}

	public void setDaySumExeTime(long daySumExeTime) {
		this.daySumExeTime = daySumExeTime;
	}

	public long getDaySumErrorCount() {
		return daySumErrorCount;
	}

	public void setDaySumErrorCount(long daySumErrorCount) {
		this.daySumErrorCount = daySumErrorCount;
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
