package com.elong.nbapi.statistic.po;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "order_create_minute")
public class MinuteDimension implements Serializable{

	private static final long serialVersionUID = 3164286653062061776L;

	public final static String FIELD_ID = "_id";

	public final static String FIELD_DATETIME = "dateTime";

	public final static String FIELD_DIMENSION = "dimension";

	public final static String FIELD_TIME = "time";
	
	public final static String FIELD_METRIC = "metric";
	
	public final static String FIELD_DIMENSIONVALUE = "dimensionValue";
	
	public final static String FIELD_DATE ="date";

	@Id
	@Field(value = "_id")
	private String id;

	@Field(value = "dateTime")
	private String dateTime;

	private String dimension;

	private String time;
	
	private String date;

	private String dimensionValue;
	
	private String metric;
	
	private Long total;

	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDimensionValue() {
		return dimensionValue;
	}

	public void setDimensionValue(String dimensionValue) {
		this.dimensionValue = dimensionValue;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

}