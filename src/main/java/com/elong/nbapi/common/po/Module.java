package com.elong.nbapi.common.po;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "C_MODULE")
public class Module implements Serializable {

	private static final long serialVersionUID = -220192065654130898L;

	public final static String FIELD_ID = "_id";
	
	public final static String FIELD_SYSTEMID = "systemId";

	@Id
	private String id;

	@Field("item_name")
	private String itemName;

	@Field("business_type")
	private String businessType;

	@Field("system_id")
	private String systemId;
	
	private List<Dimension> dimensionList;

	private BusinessSystem system;

	private List<Dimension> oneDimensionList;
	private List<Dimension> twoDimensionList;

	public Module() {
	}

	public Module(String itemName, String businessType) {
		super();
		this.itemName = itemName;
		this.businessType = businessType;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public List<Dimension> getDimensionList() {
		return dimensionList;
	}

	public void setDimensionList(List<Dimension> dimensionList) {
		this.dimensionList = dimensionList;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public BusinessSystem getSystem() {
		return system;
	}

	public void setSystem(BusinessSystem system) {
		this.system = system;
	}

	/**
	 * @return the oneDimension
	 */
	public List<Dimension> getOneDimensionList() {
		oneDimensionList = new ArrayList<Dimension>();
		
		List<Dimension> allDimensionList = getDimensionList();
		if(CollectionUtils.isEmpty(allDimensionList)){
			return oneDimensionList;
		}
		
		for (Dimension dimen : getDimensionList()) {
			if (Dimension.DIMENSION_ONE.equals(dimen.getDimensionCount())) {
				oneDimensionList.add(dimen);
			}
		}

		return oneDimensionList;
	}

	/**
	 * @param oneDimension
	 *            the oneDimension to set
	 */
	public void setOneDimensionList(List<Dimension> oneDimensionList) {
		this.oneDimensionList = oneDimensionList;
	}

	/**
	 * @return the twoDimension
	 */
	public List<Dimension> getTwoDimensionList() {
		twoDimensionList = new ArrayList<Dimension>();
		
		List<Dimension> allDimensionList = getDimensionList();
		if(CollectionUtils.isEmpty(allDimensionList)){
			return twoDimensionList;
		}
			
		
		for (Dimension dimen : getDimensionList()) {
			if (Dimension.DIMENSION_TWO.equals(dimen.getDimensionCount())) {
				twoDimensionList.add(dimen);
			}
		}

		return twoDimensionList;
	}

	/**
	 * @param twoDimension
	 *            the twoDimension to set
	 */
	public void setTwoDimensionList(List<Dimension> twoDimensionList) {
		this.twoDimensionList = twoDimensionList;
	}

}
