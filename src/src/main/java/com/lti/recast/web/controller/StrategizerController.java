package com.lti.recast.web.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.recast.service.StrategizerService;
import com.lti.recast.web.model.AnalysisReportModel;
import com.lti.recast.web.model.MigratorStatusModel;
import com.lti.recast.web.model.PrjRptAnalysisModel;
import com.lti.recast.web.model.ProjectModel;
import com.lti.recast.web.model.ReportStrategizerModel;
import com.lti.recast.web.model.StrategizerCalculatedFormulaModel;
import com.lti.recast.web.model.StrategizerOutputModel;
import com.lti.recast.web.model.StrategizerQueryModel;
import com.lti.recast.web.model.StrategizerVisualizationConvertor;

import net.sf.jsqlparser.JSQLParserException;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/strategizer")
public class StrategizerController {

	private static Logger logger = LoggerFactory.getLogger(StrategizerController.class);
	
	@Autowired(required = false)
	StrategizerService strategizerService;
	

	@GetMapping("/getTasks/{projectReportTargetConId}")
	public List<ReportStrategizerModel> getTasks(@PathVariable int projectReportTargetConId) {
		
		//System.out.println("task Id Analyzer:"+analysisTaskId);
		logger.debug("Inside get tasks");
		return strategizerService.getTasks(projectReportTargetConId);

	}
	
	
	@PostMapping("/addTask")
	@Async
	public String strategizerTask(@RequestBody ReportStrategizerModel reportTaskModel) {
		logger.info("Inside get Analysis for: " + reportTaskModel); 
		
		ReportStrategizerModel r = strategizerService.save(reportTaskModel);
		
		
		String op;
		try {
			op = strategizerService.fetchStrategizerDetails(r).get();
		} 
		catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			op = "Failed";
		}
		return op;

		//String result = strategizerService.fetchStrategizerDetails(reportTaskModel);
		
		//return projAnalysisService.getAnalysis(id);
	}
	
	
	@GetMapping("/query/{id}")
	public List<StrategizerQueryModel> getConvertedQueryData(@PathVariable int id) {
		logger.info("Inside get Query Strategizer Analysis for: " + id); 
		return strategizerService.getQueryStrategizerData(id);
	}
	
	@GetMapping("/calculatedColumn/{id}")
	public List<StrategizerCalculatedFormulaModel> getConvertedCalculatedColumnData(@PathVariable int id) {
		logger.info("Inside get Query Strategizer Analysis for: " + id); 
		return strategizerService.getCalculatedColumnStrategizerData(id);
	}
	
	@GetMapping("/visualization/{id}")
	public List<StrategizerVisualizationConvertor> getVisualizationData(@PathVariable int id) {
		logger.info("Inside get Visualization Strategizer Analysis for: " + id); 
		return strategizerService.getVisualizationStrategizerData(id);
	}
	
	@GetMapping("/migrate/{id}")
	public String strategizerCSV(@PathVariable int id) throws JSQLParserException, Exception {
		logger.info("Inside migrate for: " + id); 
		return strategizerService.migrate(id);
	}
	
	@GetMapping("/migrate/status/{id}")
	public List<MigratorStatusModel> getMigratorSatus(@PathVariable int id) {
		logger.info("Inside get Migrator Status for: " + id); 
		return strategizerService.getMigratorStatus(id);
	}
	
	
}
