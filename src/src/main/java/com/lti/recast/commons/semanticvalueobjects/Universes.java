package com.lti.recast.commons.semanticvalueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "universes")
@XmlAccessorType(XmlAccessType.FIELD)
public class Universes {
	@XmlElement
	private List<Universe> universe;

	@XmlElement
	public List<Universe> getUniverses() {
		return universe;
	}

	public void setUniverses(List<Universe> universe) {
		this.universe = universe;
	}
}