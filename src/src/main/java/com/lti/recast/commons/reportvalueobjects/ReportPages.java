package com.lti.recast.commons.reportvalueobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 10602114
 *
 */
public class ReportPages {
	private List<Page> page = new ArrayList<Page>();

	public List<Page> getPage() {
		return page;
	}

	public void setPage(List<Page> page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "ReportPages [page=" + page + "]";
	}

}
