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
public class PageFooter{

	private List<Object> pageFooterElements = new ArrayList<Object>();
	private List<List<ConditionalBlock>> conditionalBlockElements = new ArrayList<List<ConditionalBlock>>();

	public PageFooter() {
		// TODO Auto-generated constructor stub
	}

	public List<Object> getPageFooterElements() {
		return pageFooterElements;
	}

	public void setPageFooterElements(List<Object> pageFooterElements) {
		this.pageFooterElements = pageFooterElements;
	}

	public List<List<ConditionalBlock>> getConditionalBlockElements() {
		return conditionalBlockElements;
	}

	public void setConditionalBlockElements(List<List<ConditionalBlock>> conditionalBlockElements) {
		this.conditionalBlockElements = conditionalBlockElements;
	}

	@Override
	public String toString() {
		return "PageFooter [pageFooterElements=" + pageFooterElements + ", conditionalBlockElements="
				+ conditionalBlockElements + "]";
	}

}
