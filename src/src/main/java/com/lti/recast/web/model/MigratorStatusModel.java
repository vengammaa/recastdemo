package com.lti.recast.web.model;

import java.io.Serializable;

public class MigratorStatusModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int stratTaskId;
	private String reportId;
	private String reportTabId;
	private String statusMessage;
	private String startTime;
	private String endTime;
	private String macroRuntime;
	public int getStratTaskId() {
		return stratTaskId;
	}
	public void setStratTaskId(int stratTaskId) {
		this.stratTaskId = stratTaskId;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportTabId() {
		return reportTabId;
	}
	public void setReportTabId(String reportTabId) {
		this.reportTabId = reportTabId;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getMacroRuntime() {
		return macroRuntime;
	}
	public void setMacroRuntime(String macroRuntime) {
		this.macroRuntime = macroRuntime;
	}
	@Override
	public String toString() {
		return "MigratorStatusModel [stratTaskId=" + stratTaskId + ", reportId=" + reportId + ", reportTabId="
				+ reportTabId + ", statusMessage=" + statusMessage + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", macroRuntime=" + macroRuntime + "]";
	}

	
	
	
}
