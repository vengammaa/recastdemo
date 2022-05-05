/**
 * 
 */
package com.lti.recast.commons.reportvalueobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 10602114
 *
 */
public class PageHeader{
	private List<Object> pageHeaderElements = new ArrayList<Object>();
	private List<List<ConditionalBlock>> conditionalBlockElements = new ArrayList<List<ConditionalBlock>>();
	private List<String> reportTitle;
	private String logoPath;

	


	public List<String> getReportTitle() {
		return reportTitle;
	}


	public void setReportTitle(List<String> reportTitle) {
		this.reportTitle = reportTitle;
	}


	public String getLogoPath() {
		return logoPath;
	}


	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}


	public List<Object> getPageHeaderElements() {
		return pageHeaderElements;
	}


	public void setPageHeaderElements(List<Object> pageHeaderElements) {
		this.pageHeaderElements = pageHeaderElements;
	}


	public List<List<ConditionalBlock>> getConditionalBlockElements() {
		return conditionalBlockElements;
	}

	public void setConditionalBlockElements(List<List<ConditionalBlock>> combinationChartElements) {
		this.conditionalBlockElements = combinationChartElements;
	}


	@Override
	public String toString() {
		return "PageHeader [pageHeaderElements=" + pageHeaderElements + ", conditionalBlockElements="
				+ conditionalBlockElements + ", reportTitle=" + reportTitle + ", logoPath=" + logoPath + "]";
	}

	
}