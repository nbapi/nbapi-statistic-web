package com.elong.nbapi.common.po;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "C_BUSINESS_SYSTEM")
public class BusinessSystem implements Serializable {

    private static final long serialVersionUID = -2377483754585094587L;
    
    public final static String FIELD_ID = "id";

    @Id
    private String id;
    @Field(value = "system_name")
    private String systemName;
    @Field(value = "business_line")
    private String businessLine;
    @Field(value = "contact_name")
    private String contactName;
    @Field(value = "department")
    private String department;

    public BusinessSystem() {
    }

    public BusinessSystem(String id, String systemName, String businessLine, String contactName,
            String department) {
        super();
        this.systemName = systemName;
        this.businessLine = businessLine;
        this.contactName = contactName;
        this.department = department;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getBusinessLine() {
        return businessLine;
    }

    public void setBusinessLine(String businessLine) {
        this.businessLine = businessLine;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof BusinessSystem)) {
            return false;
        }

        BusinessSystem other = (BusinessSystem) obj;

        if (id == null) {
            if (other.id != null) {
                return false;
            }

        } else if (!id.equals(other.id)) {
            return false;
        }

        return true;
    }

}
