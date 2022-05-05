package com.lti.recast.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sapbo_powerbi_mapping")
public class SapboPowerbiMapping implements java.io.Serializable {
    
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String sapboComponent;
	private String powerbiComponent;
	private String powerbiComponentAvailability;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSapboComponent() {
		return sapboComponent;
	}
	public void setSapboComponent(String sapboComponent) {
		this.sapboComponent = sapboComponent;
	}
	public String getPowerbiComponent() {
		return powerbiComponent;
	}
	public void setPowerbiComponent(String powerbiComponent) {
		this.powerbiComponent = powerbiComponent;
	}
	public String getPowerbiComponentAvailability() {
		return powerbiComponentAvailability;
	}
	public void setPowerbiComponentAvailability(String powerbiComponentAvailability) {
		this.powerbiComponentAvailability = powerbiComponentAvailability;
	}
	@Override
	public String toString() {
		return "SapboPowerbiMapping [id=" + id + ", sapboComponent=" + sapboComponent + ", powerbiComponent="
				+ powerbiComponent + ", powerbiComponentAvailability=" + powerbiComponentAvailability + "]";
	}
	
	
	
}
