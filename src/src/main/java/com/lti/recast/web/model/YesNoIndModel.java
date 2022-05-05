package com.lti.recast.web.model;

public class YesNoIndModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private String name;

	public YesNoIndModel() {
	}

	public YesNoIndModel(String code, String name) {
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
		return "YesNoIndModel [code=" + code + ", name=" + name + "]";
	}

}
