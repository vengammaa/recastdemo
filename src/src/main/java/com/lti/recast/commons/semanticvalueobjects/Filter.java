package com.lti.recast.commons.semanticvalueobjects;

/**
 * @author 10614303
 *
 */
public class Filter {
	private String filterType;
	private String filterExpression;
	private String filterName;
	private String filterColumnName;
	private String tableName;
	
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	public String getFilterExpression() {
		return filterExpression;
	}
	public void setFilterExpression(String filterExpression) {
		this.filterExpression = filterExpression;
	}
	public String getFilterColumnName() {
		return filterColumnName;
	}
	public void setFilterColumnName(String filterColumnName) {
		this.filterColumnName = filterColumnName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	@Override
	public String toString() {
		return "Filter [filterType=" + filterType + ", filterExpression=" + filterExpression + ", filterName="
				+ filterName + ", filterColumnName=" + filterColumnName + ", tableName=" + tableName + "]";
	}

	

}
