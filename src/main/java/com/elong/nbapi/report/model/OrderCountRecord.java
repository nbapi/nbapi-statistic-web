package com.elong.nbapi.report.model;

public class OrderCountRecord {

	private String proxyid;
	
	private int valisum;
	
	private int valisuccess;
	
	private int valifailed;
	
	private int creasum;
	
	private int creasuccess;
	
	private int creafailed;
	
	private int prepay;
	
	private int selfpay;

	public String getProxyid() {
		return proxyid;
	}

	public void setProxyid(String proxyid) {
		this.proxyid = proxyid;
	}

	public int getValisum() {
		return valisum;
	}

	public void setValisum(int valisum) {
		this.valisum = valisum;
	}

	public int getValisuccess() {
		return valisuccess;
	}

	public void setValisuccess(int valisuccess) {
		this.valisuccess = valisuccess;
	}

	public int getValifailed() {
		return valifailed;
	}

	public void setValifailed(int valifailed) {
		this.valifailed = valifailed;
	}

	public int getCreasum() {
		return creasum;
	}

	public void setCreasum(int creasum) {
		this.creasum = creasum;
	}

	public int getCreasuccess() {
		return creasuccess;
	}

	public void setCreasuccess(int creasuccess) {
		this.creasuccess = creasuccess;
	}

	public int getCreafailed() {
		return creafailed;
	}

	public void setCreafailed(int creafailed) {
		this.creafailed = creafailed;
	}

	public int getPrepay() {
		return prepay;
	}

	public void setPrepay(int prepay) {
		this.prepay = prepay;
	}

	public int getSelfpay() {
		return selfpay;
	}

	public void setSelfpay(int selfpay) {
		this.selfpay = selfpay;
	}

}
