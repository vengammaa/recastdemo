package com.lti.recast.commons.semanticvalueobjects;

/**
 * @author 10602114
 *
 */
public class FilterColumn {
	private String tableName;
	private String filterName;
	private String filterValue;
	private String filterType;
	private String filterExpression;
	private String filterColumnName;
	private String folderName;
	private String childFolderName;
	
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
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public String getFilterValue() {
		return filterValue;
	}
	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getChildFolderName() {
		return childFolderName;
	}
	public void setChildFolderName(String childFolderName) {
		this.childFolderName = childFolderName;
	}
}
