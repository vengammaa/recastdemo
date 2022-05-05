package com.lti.recast.commons.reportvalueobjects;

/**
 * @author 10602114
 *
 */
public class Page {
	private String pageName;
	private PageHeader pageHeader;
	private PageBody pageBody;
	private PageFooter pageFooter;
	private boolean isHidden; 
	private boolean isReportSection;

	public Page() {
		// TODO Auto-generated constructor stub
	}

	public String getPageName() {
		return pageName;
	}
	
	

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public PageHeader getPageHeader() {
		return pageHeader;
	}

	public void setPageHeader(PageHeader pageHeader) {
		this.pageHeader = pageHeader;
	}

	public PageBody getPageBody() {
		return pageBody;
	}

	public void setPageBody(PageBody pageBody) {
		this.pageBody = pageBody;
	}

	public PageFooter getPageFooter() {
		return pageFooter;
	}

	public void setPageFooter(PageFooter pageFooter) {
		this.pageFooter = pageFooter;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public boolean isReportSection() {
		return isReportSection;
	}

	public void setReportSection(boolean isReportSection) {
		this.isReportSection = isReportSection;
	}

	@Override
	public String toString() {
		return "Page [pageName=" + pageName + ", pageHeader=" + pageHeader + ", pageBody=" + pageBody + ", pageFooter="
				+ pageFooter + ", isHidden=" + isHidden + ", isReportSection=" + isReportSection + "]";
	}

}
