/**
 * 
 */
package com.lti.recast.commons.reportvalueobjects;

import java.util.List;

import com.lti.recast.commons.semanticvalueobjects.Filter;



/**
 * @author 10614303
 *
 */
public class PieChartElement {
	private List<ReportColumns> pieSliceList;
	private List<ReportColumns> dimensionsList;
	private List<Filter> filterList;
	private String type;
	private String pieChartName;
	private String refQueryName;
	
	
	
	
	public String getRefQueryName() {
		return refQueryName;
	}
	public void setRefQueryName(String refQueryName) {
		this.refQueryName = refQueryName;
	}
	public List<ReportColumns> getPieSliceList() {
		return pieSliceList;
	}
	public void setPieSliceList(List<ReportColumns> pieSliceList) {
		this.pieSliceList = pieSliceList;
	}
	public List<ReportColumns> getDimensionsList() {
		return dimensionsList;
	}
	public void setDimensionsList(List<ReportColumns> dimensionsList) {
		this.dimensionsList = dimensionsList;
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
	public String getPieChartName() {
		return pieChartName;
	}
	public void setPieChartName(String pieChartName) {
		this.pieChartName = pieChartName;
	}
	
	

}
