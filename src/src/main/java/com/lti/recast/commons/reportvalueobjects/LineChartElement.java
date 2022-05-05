/**
 * 
 */
package com.lti.recast.commons.reportvalueobjects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lti.recast.commons.semanticvalueobjects.Filter;


/**
 * @author 10614303
 *
 */
public class LineChartElement {
	private List<ReportColumns> xAxisList;
	private List<ReportColumns> yAxisList;
	private List<ReportColumns> zAxisList;
	private String lineChartName;
	private List<String> queryColumns = new ArrayList<String>(); 
	Map<String, String> resultLevelComputedColumn = new LinkedHashMap<String, String>();
	
	public List<ReportColumns> getzAxisList() {
		return zAxisList;
	}
	public void setzAxisList(List<ReportColumns> zAxisList) {
		this.zAxisList = zAxisList;
	}
	private List<Filter> filterList;
	private String type;

	public List<ReportColumns> getxAxisList() {
		return xAxisList;
	}
	public void setxAxisList(List<ReportColumns> xAxisList) {
		this.xAxisList = xAxisList;
	}
	public List<ReportColumns> getyAxisList() {
		return yAxisList;
	}
	public void setyAxisList(List<ReportColumns> yAxisList) {
		this.yAxisList = yAxisList;
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
	public String getLineChartName() {
		return lineChartName;
	}
	public void setLineChartName(String lineChartName) {
		this.lineChartName = lineChartName;
	}
	public List<String> getQueryColumns() {
		return queryColumns;
	}
	public void setQueryColumns(List<String> queryColumns) {
		this.queryColumns = queryColumns;
	}
	public Map<String, String> getResultLevelComputedColumn() {
		return resultLevelComputedColumn;
	}
	public void setResultLevelComputedColumn(Map<String, String> resultLevelComputedColumn) {
		this.resultLevelComputedColumn = resultLevelComputedColumn;
	}
}