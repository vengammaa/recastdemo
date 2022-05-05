package com.lti.recast.web.model;

public class PrjRptSourceTaskModel{


		
	private String universeName;
	
	private String universeDescription;

	public String getUniverseName() {
		return universeName;
	}

	public void setUniverseName(String universeName) {
		this.universeName = universeName;
	}

	public String getUniverseDescription() {
		return universeDescription;
	}

	public void setUniverseDescription(String universeDescription) {
		this.universeDescription = universeDescription;
	}

	@Override
	public String toString() {
		return "PrjRptSourceTaskModel [universeName=" + universeName + ", universeDescription=" + universeDescription
				+ "]";
	}

	
	
}
