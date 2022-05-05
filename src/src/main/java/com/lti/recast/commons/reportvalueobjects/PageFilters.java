package com.lti.recast.commons.reportvalueobjects;

import java.util.ArrayList;
import java.util.List;

public class PageFilters {
	private List<Label> labels = new ArrayList<Label>();
	private List<ComboBox> comboBox = new ArrayList<ComboBox>();
	private List<PictField> pictFields = new ArrayList<PictField>();
	private List<ButtonField> buttonFields = new ArrayList<ButtonField>();
	
	public List<Label> getLabels() {
		return labels;
	}
	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}
	public List<ComboBox> getComboBox() {
		return comboBox;
	}
	public void setComboBox(List<ComboBox> comboBox) {
		this.comboBox = comboBox;
	}
	public List<PictField> getPictFields() {
		return pictFields;
	}
	public void setPictFields(List<PictField> pictFields) {
		this.pictFields = pictFields;
	}
	public List<ButtonField> getButtonFields() {
		return buttonFields;
	}
	public void setButtonFields(List<ButtonField> buttonFields) {
		this.buttonFields = buttonFields;
	}
}
