package com.lti.recast.tableau.dto;

import java.util.List;

import com.lti.recast.commons.dto.ReportConfigDTO;


public class TableauReportConfigDTO extends ReportConfigDTO{

	private String hostname;
	private String port;
	private String username;
	private String password;
	private String connectionType;
	private String path;
	private String contentUrl;
	private String extractType;
	
	private List<String> workbooks;

	public List<String> getWorkbooks() {
		return workbooks;
	}

	public void setWorkbooks(List<String> workbooks) {
		this.workbooks = workbooks;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public String getExtractType() {
		return extractType;
	}

	public void setExtractType(String extractType) {
		this.extractType = extractType;
	}

	@Override
	public String toString() {
		return "TableauReportConfigDTO [hostname=" + hostname + ", port=" + port + ", username=" + username
				+ ", password=" + password + ", connectionType=" + connectionType + ", path=" + path + ", contentUrl="
				+ contentUrl + ", extractType=" + extractType + ", workbooks=" + workbooks + "]";
	}
	
}
