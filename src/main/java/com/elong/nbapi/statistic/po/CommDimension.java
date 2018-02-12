package com.elong.nbapi.statistic.po;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document()
public class CommDimension implements Serializable{

	private static final long serialVersionUID = 5701113693130405263L;

	public final static String FIELD_ID = "_id";

	public final static String FIELD_DATE = "date";

	public final static String FIELD_DIMENSION = "dimension";

	public final static String FIELD_HOURRANGE = "hourRange";
	
	public final static String FIELD_METRIC = "metric";
	
	private Long total;

	@Id
	@Field(value = "_id")
	private String id;

	@Field(value = "date")
	private String date;

	private String dimension;

	private Integer hourRange;

	private String dimensionValue;
	
	private String metric;
	
	@Transient
	private String formatHourRange;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public Integer getHourRange() {
		return hourRange;
	}

	public void setHourRange(Integer hourRange) {
		this.hourRange = hourRange;
	}

	public String getDimensionValue() {
		return dimensionValue;
	}

	public void setDimensionValue(String dimensionValue) {
		this.dimensionValue = dimensionValue;
	}
	
	/**
	 * @return the metric
	 */
	public String getMetric() {
		return metric;
	}

	/**
	 * @param metric the metric to set
	 */
	public void setMetric(String metric) {
		this.metric = metric;
	}

	/**
	 * @return the formatHourRange
	 */
	public String getFormatHourRange() {
		
		formatHourRange = String.format("%02d:00", this.getHourRange());
		
		return formatHourRange;
	}

	/**
	 * @param formatHourRange the formatHourRange to set
	 */
	public void setFormatHourRange(String formatHourRange) {
		this.formatHourRange = formatHourRange;
	}

	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}

}
