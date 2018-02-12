/**   
 * @(#)ReportSystem.java	2017年12月20日	下午2:27:04	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nbapi.common.po;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * (类型功能说明描述)
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年12月20日 下午2:27:04   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		zhangyang.zhu  
 * @version		1.0  
 * @since		JDK1.7
 */
@Document(collection = "C_REPORT_SYSTEM")
public class ReportSystem implements Serializable{
	
	private static final long serialVersionUID = 958577128135927892L;
	public final static String FIELD_ID = "id";
	/**   
	 * (构造函数说明)  
	 *      
	 */
	public ReportSystem() {
	}
	
	@Id
	private String id;
	
	@Field(value="dimension_name")
	private String dimensionName;
	
	
	@Field(value="table_name")
	private String tableName;
	
	@Field(value="report_cols")
	private String reportCols;
	
	@Field(value="alias_names")
	private String aliasNames;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDimensionName() {
		return dimensionName;
	}
	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
	}
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getReportCols() {
		return reportCols;
	}
	public void setReportCols(String reportCols) {
		this.reportCols = reportCols;
	}
	public String getAliasNames() {
		return aliasNames;
	}
	public void setAliasNames(String aliasNames) {
		this.aliasNames = aliasNames;
	}
	
	 public ReportSystem(String id, String dimensionName,  String tableName,
	            String aliasNames) {
	        super();
	        this.dimensionName = dimensionName;
	        this.tableName = tableName;
	        this.aliasNames = aliasNames;
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

	        if (!(obj instanceof ReportSystem)) {
	            return false;
	        }

	        ReportSystem other = (ReportSystem) obj;

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
