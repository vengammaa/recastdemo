/**
 * 
 */
package com.lti.recast.commons.reportvalueobjects;

import java.util.Map;

/**
 * @author ASPADM-MUNDAS01
 *
 */
public class CombinationChartElement {
   private String chartName;
   private String refQuery;
   private String commonAxis;
   private String topLeftAxis;
   private String defaultChartMeasure;
   private Map<String,Boolean> typeOfCharts;
public String getChartName() {
	return chartName;
}
public void setChartName(String chartName) {
	this.chartName = chartName;
}
public String getCommonAxis() {
	return commonAxis;
}
public void setCommonAxis(String commonAxis) {
	this.commonAxis = commonAxis;
}

public String getTopLeftAxis() {
	return topLeftAxis;
}
public void setTopLeftAxis(String topLeftAxis) {
	this.topLeftAxis = topLeftAxis;
}
public String getDefaultChartMeasure() {
	return defaultChartMeasure;
}
public void setDefaultChartMeasure(String defaultChartMeasure) {
	this.defaultChartMeasure = defaultChartMeasure;
}
public Map<String,Boolean> getTypeOfCharts() {
	return typeOfCharts;
}
public void setTypeOfCharts(Map<String,Boolean> typeOfCharts) {
	this.typeOfCharts = typeOfCharts;
}
public String getRefQuery() {
	return refQuery;
}
public void setRefQuery(String refQuery) {
	this.refQuery = refQuery;
}
   
   
}
