package com.lti.recast.commons.mainvalueobjects;

import java.util.List;

import com.lti.recast.commons.reportvalueobjects.ReportData;
import com.lti.recast.commons.semanticvalueobjects.SemanticData;




public class SourceDataObject {
	private List<ReportData> reportData;
	private SemanticData semanticData;
	
	public List<ReportData> getReportData() {
		return reportData;
	}
	
	public void setReportData(List<ReportData> list) {
		this.reportData = list;
	}
	
	public SemanticData getSemanticData() {
		return semanticData;
	}
	
	public void setSemanticData(SemanticData semanticData) {
		this.semanticData = semanticData;
	}	
}
