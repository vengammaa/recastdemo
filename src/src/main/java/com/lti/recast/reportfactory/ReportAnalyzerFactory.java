package com.lti.recast.reportfactory;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lti.recast.commons.ReportConstants;
import com.lti.recast.commons.dto.ReportConfigDTO;
import com.lti.recast.commons.exception.ReportTypeNotImplementedException;
import com.lti.recast.commons.service.ReportAnalyzer;
import com.lti.recast.tableau.TableauReportAnalyzer;



public class ReportAnalyzerFactory {
	private static ReportAnalyzerFactory _instance;
	private static Logger logger = LoggerFactory.getLogger(ReportAnalyzerFactory.class);

	private ReportAnalyzerFactory() {
		// singleton
	}

	public static ReportAnalyzerFactory getInstance() {
		if (_instance == null) {
			_instance = new ReportAnalyzerFactory();
		}
		return _instance;
	}

	public ReportAnalyzer getReportAnalyzer(ReportConfigDTO config) throws ReportTypeNotImplementedException {
		ReportAnalyzer reportAnalyzer = null;
		String reportType = config.getReportType();
//		if (ReportConstants.REPORT_TYPE_COGNOS.equals(reportType)) {
//			reportAnalyzer = new CognosReportAnalyzer(config);
//		} else if (ReportConstants.REPORT_TYPE_MSTR.equals(reportType)) {
//			reportAnalyzer = new MstrReportAnalyzer(config);
//		} else if (ReportConstants.REPORT_TYPE_BO.equals(reportType)) {
//			reportAnalyzer = new BOReportAnalyzer(config);
//		}else if (ReportConstants.REPORT_TYPE_TABLEAU.equals(reportType)) {
//			reportAnalyzer = new TableauReportAnalyzer(config);
//		} 
		if (ReportConstants.REPORT_TYPE_TABLEAU.equals(reportType)) {
			reportAnalyzer = new TableauReportAnalyzer(config);
		} 
		else {
			throw new ReportTypeNotImplementedException(
					"This report type is not implemented. ReportType:" + reportType);
		}
		
		
		return reportAnalyzer;
	}
	
	public static void main(String[] args) {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(ch.qos.logback.classic.Level.INFO);
		//ReportConfigDTO config = new BoReportConfigDTO();
//		config.setReportType(ReportConstants.REPORT_TYPE_COGNOS);
//		
//		try {
//			CognosReportConfigDTO cognosConfig = (CognosReportConfigDTO) config;
//			cognosConfig.setHostName("172.21.106.208");
//			cognosConfig.setPort("9300");
//			cognosConfig.setUsername("admin");
//			cognosConfig.setPassword("Admin@2020");
//			cognosConfig.setNamespace("CognosEx");
//			cognosConfig.setPath("/content//folder[@name='Samples']//report");
//			CognosReportAnalyzer analyzer = (CognosReportAnalyzer) ReportAnalyzerFactory.getInstance().getReportAnalyzer((ReportConfigDTO) cognosConfig);
//			//analyzer.analyze().forEach(x -> logger.info(x.toString()));
//			List<CognosAnalyzerModel> o = analyzer.analyze();
//			analyzer.computeCommonality(o).forEach(x -> logger.info(x.getCognosAnalyzerModel1().getReportName() +  "\t" + x.getCognosAnalyzerModel2().getReportName() + "\t" + x.getCommonality()));
//			
//			
//			
//			
//		} 
		
	}

}

