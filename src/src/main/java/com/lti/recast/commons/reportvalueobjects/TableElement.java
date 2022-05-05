/**
 * 
 */
package com.lti.recast.commons.reportvalueobjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lti.recast.commons.semanticvalueobjects.Filter;

public class TableElement {
	private List<ReportColumns> columnList;
	private List<Filter> filterList;
	private String type;
	private boolean isTotalatBottom;
	private Map<String, String> totalExpressions;
	private String totalLabel;
	private String tableName;
	private String refQueryName;
	Map<String, Map<String, String>> listOverallGroupMap = new HashMap<String, Map<String, String>>();
	
	public Map<String, Map<String, String>> getListOverallGroupMap() {
		return listOverallGroupMap;
	}
	public void setListOverallGroupMap(
			Map<String, Map<String, String>> listOverallGroupMap) {
		this.listOverallGroupMap = listOverallGroupMap;
	}
	public String getRefQueryName() {
		return refQueryName;
	}
	public void setRefQueryName(String refQueryName) {
		this.refQueryName = refQueryName;
	}
	public List<ReportColumns> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<ReportColumns> columnList) {
		this.columnList = columnList;
	}
	
	public List<Filter> getFilterList() {
		return filterList;
	}
	public void setFilterList(List<Filter> filterList) {
		this.filterList = filterList;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isTotalatBottom() {
		return isTotalatBottom;
	}
	public void setTotalatBottom(boolean isTotalatBottom) {
		this.isTotalatBottom = isTotalatBottom;
	}
	public Map<String, String> getTotalExpressions() {
		return totalExpressions;
	}
	public void setTotalExpressions(Map<String, String> totalExpressions) {
		this.totalExpressions = totalExpressions;
	}
	public String getTotalLabel() {
		return totalLabel;
	}
	public void setTotalLabel(String totalLabel) {
		this.totalLabel = totalLabel;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	

}
