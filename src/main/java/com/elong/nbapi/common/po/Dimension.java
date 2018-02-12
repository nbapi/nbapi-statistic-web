package com.elong.nbapi.common.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

@Document(collection = "C_DIMENSION")
public class Dimension implements Serializable {

    private static final long serialVersionUID = 958577128135927892L;

    public final static String FIELD_ID = "_id";

    public final static String DIMENSION_ONE = "one";
    public final static String DIMENSION_TWO = "two";

    public final static String FIELD_DIMENSION_NAME = "dimension";
    
    public final static String FIELD_MODULE_ID = "moduleId";
    
    public final static String FIELD_DISPLAY_ORDER = "displayOrder";

    @Id
    private String id;

    @Field("dimension_name")
    private String dimensionName;

    @Field("local_name")
    private String localName;
    
    @Field("display_order")
    private Integer displayOrder;
    

   /* @Field("dimension_common")
    private String dimensionCommon;*/

    @Field("dimension_values")
    private String dimensionValues;

    @Field("dimension_count")
    private String dimensionCount;

    @Field("display_type")
    private String displayType;

    @Field("module_id")
    private String moduleId;

    /*
     * @Field("system_insert") private String system;
     */

   /* @Field("business_type")
    private String businesstype;*/

   /* @Transient
    private String dimensionOneValues;

    @Transient
    private String dimensionTwoValues;*/

    @Transient
    private List<Metric> metricList = new ArrayList<Metric>();

    public Dimension() {
    }

    public Dimension(String id, String dimensionName, String localName, String dimensionValues) {
        super();
        this.dimensionName = dimensionName;
        this.localName = localName;
        this.dimensionValues = dimensionValues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    /**
     * @return the displayOrder
     */
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    /**
     * @param displayOrder the displayOrder to set
     */
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getDimensionValues() {
        /*try {
            return JSON.parse(dimensionValues).toString();
        } catch (Exception e1) {
            new RuntimeException("维度值为空或有错误，请检查");
        }

        return "";*/
        
        return dimensionValues;
    }

    public String getDimensionValuesJson() {
        return dimensionValues;
    }

   /* public void parseDimensionValues() {
        String[] localDimensionValues = this.dimensionValues.split(",");
        String[] copyLocalDimensionValues = new String[localDimensionValues.length];
        int i = 0;
        for (String localValue : localDimensionValues) {
            copyLocalDimensionValues[i] = localValue.trim();
            i++;
        }
        this.dimensionValues = JSONArray.toJSONString(copyLocalDimensionValues);

    }*/

    public String getDimensionCount() {
        return dimensionCount;
    }

    public void setDimensionCount(String dimensionCount) {
        this.dimensionCount = dimensionCount;
    }

    public void setDimensionValues(String values) {
        this.dimensionValues = values;
    }

    /*public String getDimensionCommon() {
        return dimensionCommon;
    }

    public void setDimensionCommon(String dimensionCommon) {
        this.dimensionCommon = dimensionCommon;
    }*/

    /*public String getDimensionOneValues() {
        return dimensionOneValues;
    }

    public void setDimensionOneValues(String dimensionOneValues) {
        this.dimensionOneValues = dimensionOneValues;
    }

    public String getDimensionTwoValues() {
        return dimensionTwoValues;
    }

    public void setDimensionTwoValues(String dimensionTwoValues) {
        this.dimensionTwoValues = dimensionTwoValues;
    }*/

    /**
     * @param displayType
     *            the displayType to set
     */
    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    /**
     * @return the displayType
     */
    public String getDisplayType() {
        return displayType;
    }

    /*
     * public String getSystem() { return system; }
     * 
     * public void setSystem(String system) { this.system = system; }
     */

   /* public String getBusinesstype() {
        return businesstype;
    }

    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }*/

    /**
     * @return the metricList
     */
    public List<Metric> getMetricList() {
        return metricList;
    }

    /**
     * @param metricList
     *            the metricList to set
     */
    public void setMetricList(List<Metric> metricList) {

        this.metricList = metricList;
    }

    /**
     * @return the moduleId
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId
     *            the moduleId to set
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Dimension other = (Dimension) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
