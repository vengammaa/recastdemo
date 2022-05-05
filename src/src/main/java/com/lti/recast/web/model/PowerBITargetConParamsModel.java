package com.lti.recast.web.model;

public class PowerBITargetConParamsModel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String serviceUrl;
	private String workspace;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public String getWorkspace() {
		return workspace;
	}
	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}
	@Override
	public String toString() {
		return "PowerBITargetConParamsModel [username=" + username + ", password=" + password + ", serviceUrl="
				+ serviceUrl + ", workspace=" + workspace + "]";
	}
	
	
	
}
