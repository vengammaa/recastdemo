package com.lti.recast.commons.semanticvalueobjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "universe")


public class Universe {
	private String id;
	private String cuid;
	private String name;
	private String connected;
	private String type;
	private Outline outline;
	private String folderId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCuid() {
		return cuid;
	}

	public void setCuid(String cuid) {
		this.cuid = cuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConnected() {
		return connected;
	}

	public void setConnected(String connected) {
		this.connected = connected;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Outline getOutline() {
		return outline;
	}

	public void setOutline(Outline outline) {
		this.outline = outline;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	@Override
	public String toString() {
		return "ClassPojo [id = " + id + ", cuid = " + cuid + ", name = " + name + ", connected = " + connected
				+ ", type = " + type + ", outline = " + outline + ", folderId = " + folderId + "]";
	}
}