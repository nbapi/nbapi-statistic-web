package com.elong.nbapi.statistic.po;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class CommDimensionTime implements Serializable,Comparable<CommDimensionTime> {

	private static final long serialVersionUID = -7721081437371363979L;

	public final static String FIELD_ID = "_id";

	public final static String FIELD_DATE_TIME = "dateTime";

	public final static String FIELD_DIMENSION = "dimension";

	public final static String FIELD_TIME = "time";

	public final static String FIELD_METRIC = "metric";
	
	public final static String FIELD_DATE = "date";

	@Id
	@Field(value = "_id")
	private String id;

	@Field(value = "dateTime")
	private String dateTime;

	private String dimension;

	private String time;

	private String dimensionValue;

	private String metric;

	private Integer total;
	
	private String date;

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
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

	@Override public int compareTo(CommDimensionTime o) {

		return this.dateTime.compareTo(o.getDateTime());
	}
}
