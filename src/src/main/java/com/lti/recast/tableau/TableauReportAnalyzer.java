package com.lti.recast.tableau;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lti.recast.commons.dto.ReportConfigDTO;
import com.lti.recast.commons.service.ReportAnalyzer;
import com.lti.recast.tableau.dto.TableauReportConfigDTO;
import com.lti.recast.tableau.model.ReportData;
import com.lti.recast.tableau.service.ReportAnalyzerService;


public class TableauReportAnalyzer implements ReportAnalyzer{

	private static Logger LOG = Logger.getLogger(TableauReportAnalyzer.class);
	private  TableauReportConfigDTO config;
	
	public TableauReportAnalyzer(ReportConfigDTO config) {
		LOG.info("Creating TableauReportAnalyzer instance....");
		this.config=(TableauReportConfigDTO)config;
	}
	
	public Map<String, Object> analyze(String destDirPath, String zipFilePath) throws Exception {
		
		return ReportAnalyzerService.extractMetadata(this.config, destDirPath, zipFilePath);
				
	}
	
	public String testTableauAnalyzerConnection() throws Exception
	{
		String res = ReportAnalyzerService.testTableauConnection(this.config);
		//System.out.println("RESULT FROM SERVER::"+res);
		return res;
	}
	
	public Map<String,List<ReportData>> reportPathExtraction() throws MalformedURLException, IOException
	{
		Map<String,List<ReportData>> reportPath =  ReportAnalyzerService.extractReportFolderPath(config);
		return reportPath;
	}
	
	public Map<String,List<ReportData>> datasourcePathExtraction() throws MalformedURLException, IOException
	{
		Map<String,List<ReportData>> datasourcePath =  ReportAnalyzerService.extractDatasourceFolderPath(config);
		return datasourcePath;
	}
	
	public static void main(String[] args) throws Exception {
		
		TableauReportConfigDTO config = new TableauReportConfigDTO();
		ReportAnalyzerService.extractReportPathData();

	}

}
