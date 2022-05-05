package com.lti.recast.commons.reportvalueobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 10602114
 *
 */
public class PageBody{
	
	private List<Object> pageBodyElements = new ArrayList<Object>();
	private List<List<ConditionalBlock>> conditionalBlockElements = new ArrayList<List<ConditionalBlock>>();
	
	public PageBody() {
		// TODO Auto-generated constructor stub
	}

	public List<Object> getPageBodyElements() {
		return pageBodyElements;
	}

	public void setPageBodyElements(List<Object> pageBodyElements) {
		this.pageBodyElements = pageBodyElements;
	}

	public List<List<ConditionalBlock>> getConditionalBlockElements() {
		return conditionalBlockElements;
	}

	public void setConditionalBlockElements(List<List<ConditionalBlock>> combinationChartElements) {
		this.conditionalBlockElements = combinationChartElements;
	}

	@Override
	public String toString() {
		return "PageBody [pageBodyElements=" + pageBodyElements + ", conditionalBlockElements="
				+ conditionalBlockElements + "]";
	}
	
	
	
}