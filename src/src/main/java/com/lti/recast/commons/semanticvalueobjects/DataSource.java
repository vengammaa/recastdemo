package com.lti.recast.commons.semanticvalueobjects;

/**
 * @author 10602114
 *
 */
public class DataSource {
	private String dbSchemaName;
	private String dbOwnerName;
	private String connectionName;
	
	public String getDbSchemaName() {
		return dbSchemaName;
	}
	public void setDbSchemaName(String dbSchemaName) {
		this.dbSchemaName = dbSchemaName;
	}
	public String getDbOwnerName() {
		return dbOwnerName;
	}
	public void setDbOwnerName(String dbOwnerName) {
		this.dbOwnerName = dbOwnerName;
	}
	public String getConnectionName() {
		return connectionName;
	}
	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}
}
