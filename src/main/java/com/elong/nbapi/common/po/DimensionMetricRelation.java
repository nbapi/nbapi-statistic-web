package com.elong.nbapi.common.po;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "C_DIMENSION_METRIC_RELATION")
public class DimensionMetricRelation implements Serializable {

	private static final long serialVersionUID = 5092266400354673043L;

	public final static String FIELD_ID = "id";

	public final static String FIELD_DIMENSION_ID = "dimensionId";

	public final static String FIELD_METRIC_ID = "metricId";

	public final static String FIELD_METRIC_ORDER = "metricOrder";
	@Id
	private String id;

	@Field(value = "dimension_id")
	private String dimensionId;

	@Field(value = "metric_id")
	private String metricId;
	
	@Transient
	private String metricOrder;
	
	@Transient
	private String[] metricIds;

	public DimensionMetricRelation() {
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the metricId
	 */
	public String getMetricId() {
		return metricId;
	}

	/**
	 * @param metricId
	 *            the metricId to set
	 */
	public void setMetricId(String metricId) {
		this.metricId = metricId;
	}

	/**
	 * @return the metricIds
	 */
	public String[] getMetricIds() {
		return metricIds;
	}

	/**
	 * @param metricIds the metricIds to set
	 */
	public void setMetricIds(String[] metricIds) {
		this.metricIds = metricIds;
	}

	public String getMetricOrder() {
		return metricOrder;
	}

	public void setMetricOrder(String metricOrder) {
		this.metricOrder = metricOrder;
	}

	
}
