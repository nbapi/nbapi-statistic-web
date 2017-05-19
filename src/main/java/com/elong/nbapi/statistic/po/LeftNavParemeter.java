package com.elong.nbapi.statistic.po;

import java.io.Serializable;

import com.elong.nbapi.common.po.Dimension;

public class LeftNavParemeter implements Serializable {

	private static final long serialVersionUID = 944483908965628343L;

	private String date;
	private String businessType;
	private String dimensionName;
	private String dimensionId;
	private String time;
	
	private String metric;

	private String startDate;
	private String endDate;
	private String currentDimensionValue;
	/**
	 * 前台table显示的 条目数
	 */
	private Integer displayCount;

	/**
	 * 查询的时刻
	 */
	private Integer hourRange;
	
	private String startHourRange;
	private String endHourRange;


	/**
	 * 当前页编码
	 */
	private Integer currentPage;

	private Dimension dimension;

	
	private int isFirstLoad;
	
	private String sortKey;

	private String sortValue;
	
	private String firstSort;
	
	private String lastSort;
	
	/**   
	 * 得到sortKey的值   
	 *   
	 * @return sortKey的值
	 */
	public String getSortKey() {
		return sortKey;
	}

	/**
	 * 设置sortKey的值
	 *   
	 * @param sortKey 被设置的值
	 */
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	/**   
	 * 得到sortValue的值   
	 *   
	 * @return sortValue的值
	 */
	public String getSortValue() {
		return sortValue;
	}

	/**
	 * 设置sortValue的值
	 *   
	 * @param sortValue 被设置的值
	 */
	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}
	
	public String getFirstSort() {
		return firstSort;
	}

	public void setFirstSort(String firstSort) {
		this.firstSort = firstSort;
	}

	public String getLastSort() {
		return lastSort;
	}

	public void setLastSort(String lastSort) {
		this.lastSort = lastSort;
	}

	public int getIsFirstLoad() {
		return isFirstLoad;
	}

	public void setIsFirstLoad(int isFirstLoad) {
		this.isFirstLoad = isFirstLoad;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getCurrentDimensionValue() {
		return currentDimensionValue;
	}

	public void setCurrentDimensionValue(String currentDimensionValue) {
		this.currentDimensionValue = currentDimensionValue;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getDimensionName() {
		return dimensionName;
	}

	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
	}

	/**
	 * @return the dimension
	 */
	public Dimension getDimension() {
		return dimension;
	}

	/**
	 * @param dimension
	 *            the dimension to set
	 */
	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	/**
	 * @return the dimensionId
	 */
	public String getDimensionId() {
		return dimensionId;
	}

	/**
	 * @param dimensionId
	 *            the dimensionId to set
	 */
	public void setDimensionId(String dimensionId) {
		this.dimensionId = dimensionId;
	}

	/**
	 * @return the metric
	 */
	public String getMetric() {
		return metric;
	}

	/**
	 * @param metric
	 *            the metric to set
	 */
	public void setMetric(String metric) {
		this.metric = metric;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the displayCount
	 */
	public Integer getDisplayCount() {
		return displayCount;
	}

	/**
	 * @param displayCount
	 *            the displayCount to set
	 */
	public void setDisplayCount(Integer displayCount) {
		this.displayCount = displayCount;
	}

	/**
	 * @return the hourRange
	 */
	public Integer getHourRange() {
		return hourRange;
	}

	/**
	 * @param hourRange
	 *            the hourRange to set
	 */
	public void setHourRange(Integer hourRange) {
		this.hourRange = hourRange;
	}
	
	/**   
	 * 得到startHourRange的值   
	 *   
	 * @return startHourRange的值
	 */
	public String getStartHourRange() {
		return startHourRange;
	}

	/**
	 * 设置startHourRange的值
	 *   
	 * @param startHourRange 被设置的值
	 */
	public void setStartHourRange(String startHourRange) {
		this.startHourRange = startHourRange;
	}

	/**   
	 * 得到endHourRange的值   
	 *   
	 * @return endHourRange的值
	 */
	public String getEndHourRange() {
		return endHourRange;
	}

	/**
	 * 设置endHourRange的值
	 *   
	 * @param endHourRange 被设置的值
	 */
	public void setEndHourRange(String endHourRange) {
		this.endHourRange = endHourRange;
	}

	/**
	 * @return the currentPage
	 */
	public Integer getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            the currentPage to set
	 */
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	

	/**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((businessType == null) ? 0 : businessType.hashCode());
		result = prime * result
				+ ((dimensionName == null) ? 0 : dimensionName.hashCode());
		result = prime * result + ((metric == null) ? 0 : metric.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof LeftNavParemeter))
			return false;
		LeftNavParemeter other = (LeftNavParemeter) obj;
		if (businessType == null) {
			if (other.businessType != null)
				return false;
		} else if (!businessType.equals(other.businessType))
			return false;
		if (dimensionName == null) {
			if (other.dimensionName != null)
				return false;
		} else if (!dimensionName.equals(other.dimensionName))
			return false;
		if (metric == null) {
			if (other.metric != null)
				return false;
		} else if (!metric.equals(other.metric))
			return false;
		return true;
	}

}
