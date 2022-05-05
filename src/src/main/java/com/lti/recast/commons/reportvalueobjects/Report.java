/**
 * 
 */
package com.lti.recast.commons.reportvalueobjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 10602114
 *
 */
public class Report {
	private String reportName;
	private ReportPages reportPages;
	private PromptPages promptPages;
	private QueryResults queryResults;
	
	private Map<String,List<String>> queryFieldMap = new HashMap<String,List<String>>();
	
	public String getReportName() {
		return reportName;
	}
	
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	public ReportPages getReportPages() {
		return reportPages;
	}
	
	public void setReportPages(ReportPages reportPages) {
		this.reportPages = reportPages;
	}
	
	public PromptPages getPromptPages() {
		return promptPages;
	}
	
	public void setPromptPages(PromptPages promptPages) {
		this.promptPages = promptPages;
	}

	public QueryResults getQueryResults() {
		return queryResults;
	}

	public void setQueryResults(QueryResults queryResults) {
		this.queryResults = queryResults;
	}

	public Map<String, List<String>> getQueryFieldMap() {
		return queryFieldMap;
	}
	public void setQueryFieldMap(Map<String, List<String>> queryFieldMap) {
		this.queryFieldMap = queryFieldMap;
	}
	
}
