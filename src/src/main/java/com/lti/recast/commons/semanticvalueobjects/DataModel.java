package com.lti.recast.commons.semanticvalueobjects;

import java.util.List;
import java.util.Map;

/**
 * @author 10602114
 *
 */
public class DataModel {
	private String dataModelName;
	private boolean isDbSchemaObject;
	private Map<String,String> tableMap;
	private Map<String,Map<String, List<String>>> foldersMap;
	private List<JoinColumn> joinColumnList;
	private List<FilterColumn> filterColumnList;
	private List<Columns> columnsList; //Either Measure or Dimension based on isAggregateFunction parameter (boolean)
	
	
	public List<JoinColumn> getColumnJoinList() {
		return joinColumnList;
	}
	public void setColumnJoinList(List<JoinColumn> joinColumnList) {
		this.joinColumnList = joinColumnList;
	}
	public List<FilterColumn> getFilterColumnList() {
		return filterColumnList;
	}
	public void setFilterColumnList(List<FilterColumn> filterColumnList) {
		this.filterColumnList = filterColumnList;
	}
	public List<Columns> getColumnsList() {
		return columnsList;
	}
	public void setColumnsList(List<Columns> columnsList) {
		this.columnsList = columnsList;
	}
	public Map<String,Map<String, List<String>>> getFoldersMap() {
		return foldersMap;
	}
	public void setFoldersMap(Map<String,Map<String, List<String>>> foldersMap) {
		this.foldersMap = foldersMap;
	}
	public String getDataModelName() {
		return dataModelName;
	}
	public void setDataModelName(String dataModelName) {
		this.dataModelName = dataModelName;
	}
	public boolean isDbSchemaObject() {
		return isDbSchemaObject;
	}
	public void setDbSchemaObject(boolean isDbSchemaObject) {
		this.isDbSchemaObject = isDbSchemaObject;
	}
	public Map<String,String> getTableMap() {
		return tableMap;
	}
	public void setTableMap(Map<String,String> tableMap) {
		this.tableMap = tableMap;
	}
}