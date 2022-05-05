package com.lti.recast.commons.reportvalueobjects;

public class StyleCase {

	
	private String reportCondition;
	private String refStyleName;
	public String getReportCondition() {
		return reportCondition;
	}
	public void setReportCondition(String reportCondition) {
		this.reportCondition = reportCondition;
	}
	public String getRefStyleName() {
		return refStyleName;
	}
	public void setRefStyleName(String refStyleName) {
		this.refStyleName = refStyleName;
	}
	@Override
	public String toString() {
		return "StyleCase [reportCondition=" + reportCondition + ", refStyleName=" + refStyleName + "]";
	}
	
	
	
}
