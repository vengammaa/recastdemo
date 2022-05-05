/**
 * 
 */
package com.lti.recast.commons.reportvalueobjects;

import java.util.List;

/**
 * @author 10614303
 *
 */
public class ReportColumns {
       private String columnName;
       private String displayName;
       private String tableName;
       private boolean computedColumn;
       private String computedFormula;
       private List<ReportColumns> subColumns;
      /* private List<String> subColumnNames;
       private List<String> subDisplayNames;
       */
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public boolean isComputedColumn() {
		return computedColumn;
	}
	public void setComputedColumn(boolean computedColumn) {
		this.computedColumn = computedColumn;
	}
	public String getComputedFormula() {
		return computedFormula;
	}
	public void setComputedFormula(String computedFormula) {
		this.computedFormula = computedFormula;
	}
	/*public List<String> getSubColumnNames() {
		return subColumnNames;
	}
	public void setSubColumnNames(List<String> subColumnNames) {
		this.subColumnNames = subColumnNames;
	}
	public List<String> getSubDisplayNames() {
		return subDisplayNames;
	}
	public void setSubDisplayNames(List<String> subDisplayNames) {
		this.subDisplayNames = subDisplayNames;
	}*/
	public List<ReportColumns> getSubColumns() {
		return subColumns;
	}
	public void setSubColumns(List<ReportColumns> subColumns) {
		this.subColumns = subColumns;
	}
}
