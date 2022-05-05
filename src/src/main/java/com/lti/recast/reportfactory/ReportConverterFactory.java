package com.lti.recast.reportfactory;

import com.lti.recast.commons.ReportConstants;
import com.lti.recast.commons.dto.ReportConfigDTO;
import com.lti.recast.commons.exception.ReportTypeNotImplementedException;
import com.lti.recast.commons.service.ReportConverter;

//import com.lti.data.recast.cognosmstr.migrator.CognosReportConvertor;


public class ReportConverterFactory {
	private static ReportConverterFactory _instance;
	
	private ReportConverterFactory() {
		//singleton
	}
	
	public static ReportConverterFactory getInstance() {
		if(_instance==null) {
			_instance = new ReportConverterFactory();
		}
		return _instance;
	}
	
	public static ReportConverter getReportAnalyzer(ReportConfigDTO config) throws ReportTypeNotImplementedException {
		ReportConverter reportConverter = null;
		String reportType = config.getReportType();
		if(ReportConstants.REPORT_TYPE_COGNOS.equals(reportType)) {
			//reportConverter = new CognosReportConvertor(config);
		}else {
			throw new ReportTypeNotImplementedException("This report type is not implemented. ReportType:" + reportType);
		}
		return reportConverter;
	}

}
