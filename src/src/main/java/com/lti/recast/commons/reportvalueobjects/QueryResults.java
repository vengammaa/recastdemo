package com.lti.recast.commons.reportvalueobjects;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 10602114
 *
 */
public class QueryResults {
	private Map<String, String> queryMap = new HashMap<String, String>();
	private Map<String, String> datasourceMap = new HashMap<String, String>();
	
	public Map<String, String> getQueryMap() {
		return queryMap;
	}
	public void setQueryMap(Map<String, String> queryMap) {
		this.queryMap = queryMap;
	}
	public Map<String, String> getDatasourceMap() {
		return datasourceMap;
	}
	public void setDatasourceMap(Map<String, String> datasourceMap) {
		this.datasourceMap = datasourceMap;
	}
	@Override
	public String toString() {
		return "QueryResults [queryMap=" + queryMap + ", datasourceMap=" + datasourceMap + "]";
	}
	
	
}
