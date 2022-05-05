package com.lti.recast.commons.reportvalueobjects;

public class ConditionalBlock {
	private Object conditionalBlockContents;
	private String refVariable;
	
	public Object getConditionalBlockContents() {
		return conditionalBlockContents;
	}
	public void setConditionalBlockContents(Object conditionalBlockContents) {
		this.conditionalBlockContents = conditionalBlockContents;
	}
	public String getRefVariable() {
		return refVariable;
	}
	public void setRefVariable(String refVariable) {
		this.refVariable = refVariable;
	}
	@Override
	public String toString() {
		return "ConditionalBlock [conditionalBlockContents=" + conditionalBlockContents + ", refVariable=" + refVariable
				+ "]";
	}
	
	
	
}
