package com.elong.nbapi.report.model;

public class MethodCountRecord {

	private String id;

	private String proxyid;

	private String proxyname;

	private String appname;

	private String method;

	private long count;

	private String countdate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProxyid() {
		return proxyid;
	}

	public void setProxyid(String proxyid) {
		this.proxyid = proxyid;
	}

	public String getProxyname() {
		return proxyname;
	}

	public void setProxyname(String proxyname) {
		this.proxyname = proxyname;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String getCountdate() {
		return countdate;
	}

	public void setCountdate(String countdate) {
		this.countdate = countdate;
	}

}
