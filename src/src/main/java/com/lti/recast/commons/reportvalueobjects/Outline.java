package com.lti.recast.commons.reportvalueobjects;

import java.util.List;

import com.lti.recast.commons.semanticvalueobjects.Folder;
import com.lti.recast.commons.semanticvalueobjects.Item;

public class Outline {
	private List<Folder> folder;
	private List<Item> item;
	
	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

	public List<Folder> getFolder() {
		return folder;
	}

	public void setFolder(List<Folder> folder) {
		this.folder = folder;
	}

	@Override
	public String toString() {
		return "ClassPojo [folder = " + folder + "]";
	}
}