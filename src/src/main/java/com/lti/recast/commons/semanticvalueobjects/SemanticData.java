package com.lti.recast.commons.semanticvalueobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 10602114
 *
 */
public class SemanticData {
	private String projectName;
	private Map<String,List<String>> actualTableToAliasListMap;
	private Map<String,DataModel> dataModelMap = new ConcurrentHashMap<String,DataModel>();
	private List<DataSource> dataSourcesList = new ArrayList<DataSource>();
	
	public String getProjectName() {
		return projectName;
	}
	
	public List<DataSource> getDataSourcesList() {
		return dataSourcesList;
	}

	public void setDataSourcesList(List<DataSource> dataSourcesList) {
		this.dataSourcesList = dataSourcesList;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Map<String,DataModel> getDataModelMap() {
		return dataModelMap;
	}
	public void setDataModelMap(Map<String,DataModel> dataModelMap) {
		this.dataModelMap = dataModelMap;
	}
	public Map<String, List<String>> getActualTableToAliasListMap() {
		return actualTableToAliasListMap;
	}
	public void setActualTableToAliasListMap(
			Map<String, List<String>> actualTableToAliasListMap) {
		this.actualTableToAliasListMap = actualTableToAliasListMap;
	}
}