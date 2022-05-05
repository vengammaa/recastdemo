package com.lti.recast.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PrjRptActionType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String code;
	private String name;

	public PrjRptActionType() {
	}

	public PrjRptActionType(String code, String name) {
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

}
