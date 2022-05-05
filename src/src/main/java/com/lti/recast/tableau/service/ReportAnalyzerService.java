package com.lti.recast.tableau.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.lti.recast.tableau.dto.TableauReportConfigDTO;
import com.lti.recast.tableau.model.ReportData;


public class ReportAnalyzerService {

	private static Logger logger = Logger.getLogger(ReportAnalyzerService.class);
	
	public static Map<String, Object> extractMetadata(TableauReportConfigDTO config,String destDirPath, String zipFilePath) throws Exception {
		logger.info("Extract metadata called for Tableau"); 
		TableauAnalyzerAPI tabAnalyzerAPI = new TableauAnalyzerAPI();
		Map<String, Object> tabAnalyzerModelList = tabAnalyzerAPI.fetchTabAnalyzerModelList(config, destDirPath, zipFilePath);
		return tabAnalyzerModelList;
	}
	
//	public static void logonToTableau() throws MalformedURLException, IOException {
//		TableauAnalyzerAPI.logonAndGetTokenAndSiteID("prod-apnortheast-a.online.tableau.com", "kpra16is@cmrit.ac.in", "Kpra16is@cmr", "recastinternal");
//	}
	
	public static void getWorkbooksInfo(TableauReportConfigDTO config) {
		TableauAnalyzerAPI.fetchTableauWorkbookInfo(config);
	}
	
	public static void extractReportPathData() throws MalformedURLException, IOException {
//		TableauAnalyzerAPI.extractReportPathData();
	}
	
	public static String testTableauConnection(TableauReportConfigDTO config) {
		TableauAnalyzerAPI tabAnalyzerAPI = new TableauAnalyzerAPI();
		String result = tabAnalyzerAPI.testConnection(config);
		return result;
	}
	
	public static Map<String,List<ReportData>> extractReportFolderPath(TableauReportConfigDTO config) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		TableauAnalyzerAPI tabAnalyzerAPI = new TableauAnalyzerAPI();
		Map<String,List<ReportData>> reportPathData = tabAnalyzerAPI.extractReportPathData(config);
		
		return reportPathData;
	}
	
	public static Map<String,List<ReportData>> extractDatasourceFolderPath(TableauReportConfigDTO config) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		TableauAnalyzerAPI tabAnalyzerAPI = new TableauAnalyzerAPI();
		Map<String,List<ReportData>> datasourcePathData = tabAnalyzerAPI.extractDatasourcesPathData(config);
		
		return datasourcePathData;
	}
	
}
