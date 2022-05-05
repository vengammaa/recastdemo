/**
 * 
 */
package com.lti.recast.commons.reportvalueobjects;

import java.util.List;

/**
 * @author 10614303
 *
 */
public class DualAxisBarChartElement {
	private String valueAxis;
	private String categoryAxis;
	private List<String> ColorAxis;
	private String barChartName;
	private String refQuery;
	
	
	public String getValueAxis() {
		return valueAxis;
	}
	public void setValueAxis(String valueAxis) {
		this.valueAxis = valueAxis;
	}
	public String getCategoryAxis() {
		return categoryAxis;
	}
	public void setCategoryAxis(String categoryAxis) {
		this.categoryAxis = categoryAxis;
	}
	
	public String getBarChartName() {
		return barChartName;
	}
	public void setBarChartName(String barChartName) {
		this.barChartName = barChartName;
	}
	public String getRefQuery() {
		return refQuery;
	}
	public void setRefQuery(String refQuery) {
		this.refQuery = refQuery;
	}
	public List<String> getColorAxis() {
		return ColorAxis;
	}
	public void setColorAxis(List<String> colorAxis) {
		ColorAxis = colorAxis;
	}
	
	
}