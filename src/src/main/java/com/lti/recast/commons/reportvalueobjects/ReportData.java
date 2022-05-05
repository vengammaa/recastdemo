/**
 * 
 */
package com.lti.recast.commons.reportvalueobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReportData {
	public String reportName;
	public String modelPath;
	public ReportPages reportPages;
	public PromptPages promptPages;
	public QueryResults queryResults;
	public List<Object> queriesElements = new ArrayList<Object>();
	Map<String, List<String>> queryFields = new HashMap<String, List<String>>();
	private Map<String, Map<String,String>> computedQueryFields = new HashMap<String, Map<String,String>>();
	private Map<String, List<String>> dynamicFilters = new HashMap<String, List<String>>();
	private Map<String, List<String>> staticFilters = new HashMap<String, List<String>>();
	private ConditionalResults conditionalResults;
	private Map<String, Map<String, String>> queryDataItemMap = new HashMap<String, Map<String, String>>();
	private Map<String,Map<String,List<String>>> reportVariablesMap;

	public ConditionalResults getConditionalResults() {
		return conditionalResults;
	}

	public void setConditionalResults(ConditionalResults conditionalResults) {
		this.conditionalResults = conditionalResults;
	}

	public String getModelPath() {
		return modelPath;
	}

	public Map<String, List<String>> getQueryFields() {
		return queryFields;
	}

	public void setQueryFields(Map<String, List<String>> queryFields) {
		this.queryFields = queryFields;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

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

	public List<Object> getQueriesElements() {
		return queriesElements;
	}

	public void setQueriesElements(List<Object> queriesElements) {
		this.queriesElements = queriesElements;
	}

	public Map<String, List<String>> getDynamicFilters() {
		return dynamicFilters;
	}

	public void setDynamicFilters(Map<String, List<String>> dynamicFilters) {
		this.dynamicFilters = dynamicFilters;
	}

	public Map<String, List<String>> getStaticFilters() {
		return staticFilters;
	}

	public void setStaticFilters(Map<String, List<String>> staticFilters) {
		this.staticFilters = staticFilters;
	}

	public Map<String, Map<String,String>> getComputedQueryFields() {
		return computedQueryFields;
	}

	public void setComputedQueryFields(Map<String, Map<String,String>> computedQueryFields) {
		this.computedQueryFields = computedQueryFields;
	}

	public Map<String, Map<String, String>> getQueryDataItemMap() {
		return queryDataItemMap;
	}

	public void setQueryDataItemMap(Map<String, Map<String, String>> queryDataItemMap) {
		this.queryDataItemMap = queryDataItemMap;
	}

	public Map<String,Map<String,List<String>>> getReportVariablesMap() {
		return reportVariablesMap;
	}

	public void setReportVariablesMap(Map<String,Map<String,List<String>>> reportVariablesMap) {
		this.reportVariablesMap = reportVariablesMap;
	}

	@Override
	public String toString() {
		return "ReportData [reportName=" + reportName + ", modelPath=" + modelPath + ", reportPages=" + reportPages
				+ ", promptPages=" + promptPages + ", queryResults=" + queryResults + ", queriesElements="
				+ queriesElements + ", queryFields=" + queryFields + ", computedQueryFields=" + computedQueryFields
				+ ", dynamicFilters=" + dynamicFilters + ", staticFilters=" + staticFilters + ", conditionalResults="
				+ conditionalResults + ", queryDataItemMap=" + queryDataItemMap + ", reportVariablesMap="
				+ reportVariablesMap + "]";
	}

	
	
}