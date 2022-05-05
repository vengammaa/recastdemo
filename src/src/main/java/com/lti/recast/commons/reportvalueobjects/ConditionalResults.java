package com.lti.recast.commons.reportvalueobjects;

import java.util.ArrayList;
import java.util.List;

public class ConditionalResults {
	
	private String name;
	List<StyleCase> styleCaseList =  new ArrayList<StyleCase>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<StyleCase> getStyleCaseList() {
		return styleCaseList;
	}
	public void setStyleCaseList(List<StyleCase> styleCaseList) {
		this.styleCaseList = styleCaseList;
	}
	@Override
	public String toString() {
		return "ConditionalResults [name=" + name + ", styleCaseList=" + styleCaseList + "]";
	}
	
	

	
}
