package com.elong.nbapi.report.model;

public class ProdSummary {

	private int id;
	
	private String invDate;
	
	private int prodCount;
	
	private int sellProdCount;
	
	private int prePayCount;
	
	private int selfPayCount;
	
	private int roomTypeCount;
	
	private int hotelCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInvDate() {
		return invDate;
	}

	public void setInvDate(String invDate) {
		this.invDate = invDate;
	}

	public int getProdCount() {
		return prodCount;
	}

	public void setProdCount(int prodCount) {
		this.prodCount = prodCount;
	}

	public int getSellProdCount() {
		return sellProdCount;
	}

	public void setSellProdCount(int sellProdCount) {
		this.sellProdCount = sellProdCount;
	}

	public int getPrePayCount() {
		return prePayCount;
	}

	public void setPrePayCount(int prePayCount) {
		this.prePayCount = prePayCount;
	}

	public int getSelfPayCount() {
		return selfPayCount;
	}

	public void setSelfPayCount(int selfPayCount) {
		this.selfPayCount = selfPayCount;
	}

	public int getRoomTypeCount() {
		return roomTypeCount;
	}

	public void setRoomTypeCount(int roomTypeCount) {
		this.roomTypeCount = roomTypeCount;
	}

	public int getHotelCount() {
		return hotelCount;
	}

	public void setHotelCount(int hotelCount) {
		this.hotelCount = hotelCount;
	}

}
