package com.lti.recast.web.model;

public class TableauDatasourceMap {
	private String tableName;
	private String datasourceName;
	private String connectionClass;
	private String dbName;
	private String columnName;
	private String reportPath;
	private String worksheetName;
	private String dataType;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getDatasourceName() {
		return datasourceName;
	}
	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}
	public String getReportPath() {
		return reportPath;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
	public String getWorksheetName() {
		return worksheetName;
	}
	public void setWorksheetName(String worksheetName) {
		this.worksheetName = worksheetName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getConnectionClass() {
		return connectionClass;
	}
	public void setConnectionClass(String connectionClass) {
		this.connectionClass = connectionClass;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	@Override
	public String toString() {
		return "TableauDatasourceMap [tableName=" + tableName + ", datasourceName=" + datasourceName
				+ ", connectionClass=" + connectionClass + ", dbName=" + dbName + ", columnName=" + columnName
				+ ", reportPath=" + reportPath + ", worksheetName=" + worksheetName + ", dataType=" + dataType + "]";
	}
	
	
}
