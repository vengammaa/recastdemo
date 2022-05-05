package com.lti.recast.web.model;

public class PrjRptActionTypeModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private String name;

	public PrjRptActionTypeModel() {
	}

	public PrjRptActionTypeModel(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "PrjRptActionTypeModel [code=" + code + ", name=" + name + "]";
	}

}
