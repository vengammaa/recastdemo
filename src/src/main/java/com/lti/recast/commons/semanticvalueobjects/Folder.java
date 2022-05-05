package com.lti.recast.commons.semanticvalueobjects;

import java.util.List;

public class Folder {
	private String id;
	private String description;
	private String name;
	private List<Item> item;
	private List<Folder> folder;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Folder> getFolder() {
		return folder;
	}

	public void setFolder(List<Folder> folder) {
		this.folder = folder;
	}

	@Override
	public String toString() {
		return "ClassPojo [id = " + id + ", description = " + description + ", name = " + name + ", item = " + item
				+ "]";
	}
}