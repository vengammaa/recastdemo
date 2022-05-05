package com.lti.recast.commons.semanticvalueobjects;


public class Columns {

	private String tableName;
	private String columnName;
	private String folderName;
	private String childFolderName;
	//private DataType dataType;
	private String displayName;
	private String aggregateFunc;
	private String colType;
	private boolean isAggregateFunction = false;
	
	public boolean isAggregateFunction() {
		return isAggregateFunction;
	}
	public void setAggregateFunction(boolean isAggregateFunction) {
		this.isAggregateFunction = isAggregateFunction;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getAggregateFunc() {
		return aggregateFunc;
	}
	public void setAggregateFunc(String aggregateFunc) {
		this.aggregateFunc = aggregateFunc;
	}
	public String getColType() {
		return colType;
	}
	public void setColType(String colType) {
		this.colType = colType;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
//	public DataType getDataType() {
//		return dataType;
//	}
//	public void setDataType(DataType dataType) {
//		this.dataType = dataType;
//	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this==obj)
			return true;
		
		Columns col=(Columns)obj;
		if(childFolderName!=null){
			if((this.displayName.equals(col.displayName))&&(this.folderName.equals(col.folderName))&&(this.childFolderName.equals(col.childFolderName))){
				return true;
			} else {
				return false;
			}
		} else{
			if((this.displayName.equals(col.displayName))&&(this.folderName.equals(col.folderName))){
				return true;
			} else  				{
				return false;
			}
		}	
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int prime=31;
		prime=prime*31+displayName.hashCode();
		prime=prime*17+folderName.hashCode();
		prime=prime*13+childFolderName.hashCode();
		
		return prime;
	}
}