package com.elong.nbapi.common.po;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "C_METRIC")
public class Metric {

	public final static String FIELD_ID = "id";

	public final static String FIELD_NAME = "name";

	public final static String FIELD_DISPLAY_NAME = "displayName";

	public final static String FIELD_FORMULA = "formula";
	
	public final static String FIELD_ORDER = "order";

	private String id;
	private String name;
	private String displayName;
	private String formula;
	private String order;
	
	public Metric() {
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the formula
	 */
	public String getFormula() {
		return formula;
	}

	/**
	 * @param formula
	 *            the formula to set
	 */
	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
