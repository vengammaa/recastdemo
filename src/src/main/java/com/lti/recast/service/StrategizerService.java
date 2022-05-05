package com.lti.recast.service;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.recast.jpa.entity.AnalysisReport;
import com.lti.recast.jpa.entity.AnalysisReportsTable;
import com.lti.recast.jpa.entity.AnalysisSemanticColumns;
import com.lti.recast.jpa.entity.ReportStrategizer;
import com.lti.recast.jpa.entity.StrategizerCalculatedColumn;
import com.lti.recast.jpa.entity.StrategizerQueryConversion;
import com.lti.recast.jpa.entity.StrategizerVisualizationConversion;
import com.lti.recast.jpa.entity.VisualDetails;
import com.lti.recast.jpa.repository.AnalysisReportRepository;
import com.lti.recast.jpa.repository.AnalysisReportTableRepository;
import com.lti.recast.jpa.repository.AnalysisSemanticColumnRepository;
import com.lti.recast.jpa.repository.MigratorStatusRepository;
import com.lti.recast.jpa.repository.PrjRptTargetConParamsRepository;
import com.lti.recast.jpa.repository.ReportStrategizerRepository;
import com.lti.recast.jpa.repository.StrategizerCalculatedColumnRepository;
import com.lti.recast.jpa.repository.StrategizerCalculationsRepository;
import com.lti.recast.jpa.repository.StrategizerDatasourceModelRepository;
import com.lti.recast.jpa.repository.StrategizerMetadataColumnRepository;
import com.lti.recast.jpa.repository.StrategizerQueryConversionRepository;
import com.lti.recast.jpa.repository.StrategizerVisualConversionRepository;
import com.lti.recast.jpa.repository.TaskStatusRepository;
import com.lti.recast.jpa.repository.VisualDetailsRepository;

import com.lti.recast.util.CSVGenerationUtility;
import com.lti.recast.util.EntityBuilder;
import com.lti.recast.util.ModelBuilder;
import com.lti.recast.web.model.BOTableauMigratorModel;
import com.lti.recast.web.model.MigratorStatusModel;
import com.lti.recast.web.model.PowerBITargetConParamsModel;
import com.lti.recast.web.model.PrjRptTargetConParamsModel;
import com.lti.recast.web.model.ReportStrategizerModel;
import com.lti.recast.web.model.StrategizerCalculatedFormulaModel;
import com.lti.recast.web.model.StrategizerCalculationsModel;
import com.lti.recast.web.model.StrategizerDatasourceModelModel;
import com.lti.recast.web.model.StrategizerMetadataColumnModel;
import com.lti.recast.web.model.StrategizerQueryModel;
import com.lti.recast.web.model.StrategizerVisualizationConvertor;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;



@Service
public class StrategizerService {

	private static final Logger logger = LoggerFactory.getLogger(StrategizerService.class);
	
	@Autowired(required = false)
	private AnalysisReportRepository analysisReportRepository;
	
	@Autowired(required = false)
	private AnalysisSemanticColumnRepository semanticColumnRepository;
	
	@Autowired(required = false)
	private VisualDetailsRepository visualDetailsRepository;
	
	
	@Autowired(required = false)
	private AnalysisReportTableRepository analysisReportTableRepository;
	
	@Autowired(required = false)
	private StrategizerQueryService strategizerQueryService;
	
	@Autowired(required = false)
	private StrategizerQueryConversionRepository strategizerQueryConversionRepository;
	
	@Autowired(required = false)
	private StrategizerCalculatedColumnRepository strategizerCalculatedColumnRepository;
	
	@Autowired(required = false)
	private StrategizerCalculatedFormulaService strategizerCalculatedFormulaService;
	
	@Autowired(required = false)
	private ReportStrategizerRepository reportStrategizerRepository;
	
	@Autowired(required = false)
	private TaskStatusRepository taskStatusRepository;
	
	@Autowired(required = false)
	private StrategizerVisualConvertorSerice strategizerVisualConvertorSerice;
	
	@Autowired(required = false)
	private StrategizerVisualConversionRepository strategizerVisualConvertorRepo;
	
	@Autowired(required = false)
	private StrategizerMetadataColumnRepository strategizerMetadataColumnRepository;

	@Autowired(required = false)
	private StrategizerDatasourceModelRepository strategizerDatasourceModelRepository;
	
	@Autowired(required = false)
	private StrategizerCalculationsRepository strategizerCalculationsRepository;
	
	@Autowired(required = false)
	private PrjRptTargetConParamsRepository prjRptTargetConParamsRepository;
	
	@Autowired(required = false)
	private MigratorStatusRepository migratorStatusRepository;
	
	@Transactional
	public ReportStrategizerModel save(ReportStrategizerModel reportTaskModel) {
		// TODO Auto-generated method stub
		
		ReportStrategizer strategizerTask = EntityBuilder.reportStrategizerBuilder(reportTaskModel);
		strategizerTask = reportStrategizerRepository.save(strategizerTask);
				
		ReportStrategizerModel model = ModelBuilder.strategizerModelBuilder(strategizerTask);
		model.setSelectedReports(reportTaskModel.getSelectedReports());
		return model;
	}
	
	

	@Async
	@Transactional
	public CompletableFuture<String> fetchStrategizerDetails(ReportStrategizerModel r) {
		// TODO Auto-generated method stub
		
		ReportStrategizer p = reportStrategizerRepository.findById(r.getId()).get();
		p.setTaskStatus(taskStatusRepository.findById("INPROG").get());
		
		reportStrategizerRepository.saveAndFlush(p);
		
		
		
		//Fetch Analysis Report Data
		
		if(r.getSourceReportType().equalsIgnoreCase("BO") && r.getTargetReportType().equalsIgnoreCase("PowerBI"))
		{
			try
			{
				
					int analysisTaskId = r.getAnalysisTaskId();
					
					List<StrategizerQueryConversion> strategizerQueryConversionList = new LinkedList<StrategizerQueryConversion>();
					
					List<StrategizerCalculatedColumn> strategizerCalculatedColumnList = new LinkedList<StrategizerCalculatedColumn>();

					List<StrategizerVisualizationConversion> strategizerVisualizationList = new LinkedList<StrategizerVisualizationConversion>();
					
					List<AnalysisReport> analysisReportList = analysisReportRepository.findByPrjRptAnalysisId(analysisTaskId);
					
					//Fetch Semantic Details
					
					List<AnalysisSemanticColumns> semanticColumns= semanticColumnRepository.findByTaskId(analysisTaskId);
					
					//Fetch Analysis Report Table Table Data
					
					List<AnalysisReportsTable> analysisReportTable= analysisReportTableRepository.findByTaskId(analysisTaskId);
					
					//Fetch Visualization Details

					List<VisualDetails> visualDetailsReportList = visualDetailsRepository.findByTaskId(analysisTaskId);
					
					//Filtered Analysis Report List
					 List<AnalysisReport> filteredAnalysisReportList = analysisReportList.stream()
						      .filter(x ->r.getSelectedReports().contains(x.getReportId().toString()))
						      .collect(Collectors.toList());
					
					 //Filtered Analysis Report Table List
					 
					 List<AnalysisReportsTable> filteredAnalysisReportTableList = analysisReportTable.stream()
						      .filter(x ->r.getSelectedReports().contains(x.getReportId().toString()))
						      .collect(Collectors.toList());
					 
					 //Filtered Visual Details Report List

					 List<VisualDetails> filteredVisualDetailsList = visualDetailsReportList.stream()
							 .filter(x ->r.getSelectedReports().contains(x.getReportId().toString()))
						      .collect(Collectors.toList());
					 
					 
					 //Query Conversion
					 
					 List<StrategizerQueryModel> strategizerQueryModelList = strategizerQueryService.saveQueryData(filteredAnalysisReportList,filteredAnalysisReportTableList,semanticColumns);
					 
					 strategizerQueryModelList.forEach(x->{
							
							StrategizerQueryConversion strategizerQueryConversion = EntityBuilder.StrategizerQueryEntityBuilder(x);
							
							strategizerQueryConversion.setStratTaskId(r.getId());

							strategizerQueryConversionList.add(strategizerQueryConversion);
						});
						strategizerQueryConversionRepository.saveAll(strategizerQueryConversionList);
						
						//Calculated Formula Conversion Call
						
						List<StrategizerCalculatedFormulaModel> strategizerCalculatedFormulaList = strategizerCalculatedFormulaService.saveFormulaData(filteredVisualDetailsList,filteredAnalysisReportList,strategizerQueryConversionList);
						
						strategizerCalculatedFormulaList.forEach(x->{
							
							StrategizerCalculatedColumn strategizerCalculatedColumn = EntityBuilder.StrategizerCalculatedColumnBuilder(x);
							
							strategizerCalculatedColumn.setStratTaskId(r.getId());

							strategizerCalculatedColumnList.add(strategizerCalculatedColumn);
						});
						strategizerCalculatedColumnRepository.saveAll(strategizerCalculatedColumnList);
						

						//Visualization Conversion Call
						
						List<StrategizerVisualizationConvertor> strategizerVisualList = strategizerVisualConvertorSerice.saveVisualConversionData(filteredVisualDetailsList,strategizerCalculatedFormulaList);
						
						strategizerVisualList.forEach(x->{
							if(x!=null)
							{
								StrategizerVisualizationConversion strategizerVisualConvertor = EntityBuilder.strategizerVisualConvertor(x);	
								strategizerVisualConvertor.setStratTaskId(r.getId());
								strategizerVisualizationList.add(strategizerVisualConvertor);
							}
							
						});
						strategizerVisualConvertorRepo.saveAll(strategizerVisualizationList);
				
					
					/* Set status of task as completed, save and flush the changes */
					p.setTaskStatus(taskStatusRepository.findById("FIN").get());
					reportStrategizerRepository.saveAndFlush(p);
					logger.debug("Status of " + p.getTaskName() + " is now set to " + p.getTaskStatus().getName() + ".");
			}
			catch(Exception e)
			{
				p.setTaskStatus(taskStatusRepository.findById("FAIL").get());
				reportStrategizerRepository.saveAndFlush(p);
				logger.debug("Status of " + p.getTaskName() + " is now set to " + p.getTaskStatus().getName() + ".");
				e.printStackTrace();
			}
		}
		else if (r.getSourceReportType().equalsIgnoreCase("BO") && r.getTargetReportType().equalsIgnoreCase("Tableau"))
		{
			System.out.println("In Target as Tableau");
			
			try
			{
				int analysisTaskId = r.getAnalysisTaskId();
				
				List<StrategizerQueryConversion> strategizerQueryConversionList = new LinkedList<StrategizerQueryConversion>();
				
			//	List<StrategizerCalculatedColumn> strategizerCalculatedColumnList = new LinkedList<StrategizerCalculatedColumn>();

			//	List<StrategizerVisualizationConversion> strategizerVisualizationList = new LinkedList<StrategizerVisualizationConversion>();
				
				List<AnalysisReport> analysisReportList = analysisReportRepository.findByPrjRptAnalysisId(analysisTaskId);
				
				//Fetch Semantic Details
				
				List<AnalysisSemanticColumns> semanticColumns= semanticColumnRepository.findByTaskId(analysisTaskId);
				
				//Fetch Analysis Report Table Table Data
				
				List<AnalysisReportsTable> analysisReportTable= analysisReportTableRepository.findByTaskId(analysisTaskId);
				
				//Fetch Visualization Details

			//	List<VisualDetails> visualDetailsReportList = visualDetailsRepository.findByTaskId(analysisTaskId);
				
				//Filtered Analysis Report List
				 List<AnalysisReport> filteredAnalysisReportList = analysisReportList.stream()
					      .filter(x ->r.getSelectedReports().contains(x.getReportId().toString()))
					      .collect(Collectors.toList());
				
				 //Filtered Analysis Report Table List
				 
				 List<AnalysisReportsTable> filteredAnalysisReportTableList = analysisReportTable.stream()
					      .filter(x ->r.getSelectedReports().contains(x.getReportId().toString()))
					      .collect(Collectors.toList());
				 
				 //Filtered Visual Details Report List

//				 List<VisualDetails> filteredVisualDetailsList = visualDetailsReportList.stream()
//						 .filter(x ->r.getSelectedReports().contains(x.getReportId().toString()))
//					      .collect(Collectors.toList());
				 
				 List<StrategizerQueryModel> strategizerQueryModelList = strategizerQueryService.saveQueryData(filteredAnalysisReportList,filteredAnalysisReportTableList,semanticColumns);
				 
				 strategizerQueryModelList.forEach(x->{
						
						StrategizerQueryConversion strategizerQueryConversion = EntityBuilder.StrategizerQueryEntityBuilder(x);
						
						strategizerQueryConversion.setStratTaskId(r.getId());

						strategizerQueryConversionList.add(strategizerQueryConversion);
					});
					strategizerQueryConversionRepository.saveAll(strategizerQueryConversionList);
				
					
					/* Set status of task as completed, save and flush the changes */
					p.setTaskStatus(taskStatusRepository.findById("FIN").get());
					reportStrategizerRepository.saveAndFlush(p);
					logger.debug("Status of " + p.getTaskName() + " is now set to " + p.getTaskStatus().getName() + ".");
	
				
			}
			catch(Exception e)
			{
				p.setTaskStatus(taskStatusRepository.findById("FAIL").get());
				reportStrategizerRepository.saveAndFlush(p);
				logger.debug("Status of " + p.getTaskName() + " is now set to " + p.getTaskStatus().getName() + ".");
				e.printStackTrace();
			}
			
			
			
			
			
		}
		
		String op = "Status of " + p.getTaskName() + " is now set to " + p.getTaskStatus().getName() + ".";
		return CompletableFuture.completedFuture(op);	 

	}
	
	
	@Transactional
	public List<ReportStrategizerModel> getTasks(int projectReportTargetConId) {
		// TODO Auto-generated method stub
		
			logger.info("inside gettasks");
			return reportStrategizerRepository.findAllByProjectReportTargetConId(projectReportTargetConId).stream().map(ModelBuilder::strategizerModelBuilder).collect(Collectors.toList());
		
	}

	@Transactional
	public List<StrategizerQueryModel> getQueryStrategizerData(int id) {
		// TODO Auto-generated method stub
		return strategizerQueryConversionRepository.findByStratTaskId(id).stream().map(ModelBuilder::strategizerModelQueryBuider).collect(Collectors.toList());	

	}
	
	@Transactional
	public List<StrategizerCalculatedFormulaModel> getCalculatedColumnStrategizerData(int id) {
		// TODO Auto-generated method stub
		return strategizerCalculatedColumnRepository.findByStratTaskId(id).stream().map(ModelBuilder::strategizerModelCalculatedColumnBuilder).collect(Collectors.toList());
	}

	@Transactional
	public List<StrategizerVisualizationConvertor> getVisualizationStrategizerData(int id) {
		// TODO Auto-generated method stub
		return strategizerVisualConvertorRepo.findByStratTaskId(id).stream().map(ModelBuilder::strategizerModelVisualizationBuilder).collect(Collectors.toList());
	}

	@Transactional
	public List<MigratorStatusModel> getMigratorStatus(int id) {
		return migratorStatusRepository.findByStratTaskId(id).stream().map(ModelBuilder::migratorStatusModelBuilder).collect(Collectors.toList());
	}
	
	
	
	public String migrate(int stratId) throws JSQLParserException, Exception {
		// TODO Auto-generated method stub
		
		
		//List<ReportStrategizerModel> reportStrategizer = reportStrategizerRepository.findById(stratId).stream().map(ModelBuilder::);
		
		ReportStrategizer p = reportStrategizerRepository.findById(stratId).get();
		
		if(p.getSourceReportType().equalsIgnoreCase("BO"))
		{
			if(p.getTargetReportType().equalsIgnoreCase("PowerBI"))
			{
				List<StrategizerQueryModel> strategizerQueryModelList =strategizerQueryConversionRepository.findByStratTaskId(stratId).stream().map(ModelBuilder::strategizerModelQueryBuider).collect(Collectors.toList()); 
				
				//System.out.println("Strategizer Query List -"+strategizerQueryModelList);
				
				
				Set<String> reportIdSet = new LinkedHashSet<String>();
				
				
				strategizerQueryModelList.forEach(x->{
					reportIdSet.add(x.getReportId());
				});
				
				CSVGenerationUtility c = new CSVGenerationUtility();
				c.createFolder(reportIdSet,stratId);
				
				c.generateCSVForQuery(strategizerQueryModelList,reportIdSet,stratId);
				
				c.generateCSVQueryFields(strategizerQueryModelList,reportIdSet,stratId);
				
				
				List<StrategizerCalculatedFormulaModel> strategizerCalculatedFormulaList = strategizerCalculatedColumnRepository.findByStratTaskId(stratId).
									stream().map(ModelBuilder::strategizerModelCalculatedColumnBuilder).collect(Collectors.toList());
				
//				Map<String,List<String>> reportTabMap = new LinkedHashMap<String, List<String>>();
//				
//				strategizerCalculatedFormulaList.forEach(x->{
//					
//					String key = x.getReportId();
//					
//					if(reportTabMap.containsKey(key))
//					{
//						List<String> reportTabList = reportTabMap.get(key);
//						reportTabList.add(x.getReportTabId());
//						reportTabMap.put(key, reportTabList);
//					}
//					else
//					{
//						List<String> reportTabList = new LinkedList<String>();
//						reportTabList.add(x.getReportTabId());
//						reportTabMap.put(key, reportTabList);
//					}
//					
//					
//				});
//				
//				System.out.println("Report Tab Map::"+reportTabMap);
				
				c.generateCSVForCalculatedVariablesMeasures(strategizerCalculatedFormulaList,reportIdSet,stratId);
				
				List<StrategizerVisualizationConvertor> strategizerVisualConvertorList = strategizerVisualConvertorRepo.findByStratTaskId(stratId).
						stream().map(ModelBuilder::strategizerModelVisualizationBuilder).collect(Collectors.toList());
				
				System.out.println("visualization List ::"+strategizerVisualConvertorList);
				
				c.generateCSVForVisualization(strategizerVisualConvertorList,reportIdSet,stratId);
				
				
				int analysisTaskId = p.getAnalysisTaskId();
				int projectReportTargetConId = p.getProjectReportTargetCon().getId();
				
				System.out.println("Project report target conn id::" + projectReportTargetConId);
				
				List<PrjRptTargetConParamsModel> prjRptTargetConParamsList = prjRptTargetConParamsRepository.findByProjectReportTargetConId(projectReportTargetConId).stream().map(ModelBuilder::prjRptTargetConParamsModelBuilder).collect(Collectors.toList());
				
				System.out.println("Prj Rpt Target Con Model::" + prjRptTargetConParamsList);
				
				PowerBITargetConParamsModel pbiTargetConParams = new PowerBITargetConParamsModel();
				
				for (PrjRptTargetConParamsModel prjRptTargetConParams : prjRptTargetConParamsList) {
					if (prjRptTargetConParams.getRptTargetConParamType().getCode().equals("PBI_WORKSPACE")) {
						pbiTargetConParams.setWorkspace(prjRptTargetConParams.getRptTargetConParamValue());
					} else if (prjRptTargetConParams.getRptTargetConParamType().getCode().equals("PBI_SERVICE_URL")) {
						pbiTargetConParams.setServiceUrl(prjRptTargetConParams.getRptTargetConParamValue());
					} else if (prjRptTargetConParams.getRptTargetConParamType().getCode().equals("PBI_USERNAME")) {
						pbiTargetConParams.setUsername(prjRptTargetConParams.getRptTargetConParamValue());
					} else if (prjRptTargetConParams.getRptTargetConParamType().getCode().equals("PBI_PASSWORD")) {
						pbiTargetConParams.setPassword(prjRptTargetConParams.getRptTargetConParamValue());
					}
				}
				
				List<AnalysisReport> analysisReportList = analysisReportRepository.findByPrjRptAnalysisId(analysisTaskId);
				
				for(int i=0;i<analysisReportList.size();i++)
				{
					AnalysisReport a = analysisReportList.get(i);
					String reportId = a.getReportId();
					//rid = reportId;
					String reportName = a.getReportName();
				
					c.generateCSVPowerBiPublish(reportName, reportId, pbiTargetConParams,stratId);
					
				}
				
				callClientPythonProgram();
				callServerPythonProgram();
				
				
				return "Success";
			}
			else if(p.getTargetReportType().equalsIgnoreCase("Tableau"))
			{
				System.out.println("In Tableau Migrator");
				
				BOTableauMigratorModel t = new BOTableauMigratorModel();
				
				List<StrategizerQueryModel> strategizerQueryModelList =strategizerQueryConversionRepository.findByStratTaskId(stratId).stream().map(ModelBuilder::strategizerModelQueryBuider).collect(Collectors.toList()); 
				
				List<StrategizerVisualizationConvertor> strategizerVisualConvertorList = strategizerVisualConvertorRepo.findByStratTaskId(stratId).
						stream().map(ModelBuilder::strategizerModelVisualizationBuilder).collect(Collectors.toList());
				
				List<StrategizerCalculatedFormulaModel> strategizerCalculatedFormulaList = strategizerCalculatedColumnRepository.findByStratTaskId(stratId).
						stream().map(ModelBuilder::strategizerModelCalculatedColumnBuilder).collect(Collectors.toList());
				
				List<StrategizerMetadataColumnModel> strategizerMetadataColumnList = strategizerMetadataColumnRepository.findByStratTaskId(stratId).
						stream().map(ModelBuilder::strategizerModelMetadataColumnBuilder).collect(Collectors.toList());
				
				List<StrategizerDatasourceModelModel> strategizerDataModelList = strategizerDatasourceModelRepository.findByStratTaskId(stratId).
						stream().map(ModelBuilder::strategizerModelDataModelBuilder).collect(Collectors.toList());
				
				List<StrategizerCalculationsModel> strategizerCalculationModelList = strategizerCalculationsRepository.findByStratTaskId(stratId).
						stream().map(ModelBuilder::strategizerCalculationsModelBuilder).collect(Collectors.toList());
				
				t.setStategizerId(stratId);
				t.setQueryModelList(strategizerQueryModelList);
				t.setVisualConvertorList(strategizerVisualConvertorList);
				t.setCalculatedFormulaList(strategizerCalculatedFormulaList);
				t.setMetadataColumnList(strategizerMetadataColumnList);
				t.setDatasourceModelList(strategizerDataModelList);
				t.setCalculationsList(strategizerCalculationModelList);
				
			//	MigratorModel model = ModelBuilder.BOTableauModelBuilder(t);
				
			//	BOTableauReportMigrator s = new BOTableauReportMigrator();
				
				//String result = s.migrate(model);
				
				return "Success";
			}
		}
		
		
		
		return "Failure";
	}
	
    public void callClientPythonProgram() throws Exception {
    	
    	  FileReader reader=new FileReader(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"/strategizer/strategizer.properties");
		  Properties p=new Properties();
		  p.load(reader);
		  String migratorPath = p.getProperty("MigratorPath");
		 
		  Path path = Paths.get(migratorPath);
		  String clientFilename = p.getProperty("ClientPythonProgramName");
		  
		  
		  String clientCommand = "cmd /c start cmd.exe /K \"cd /d \"";
		  clientCommand+= path.toString()+"\"&&python "+clientFilename+"\"";
	     //   clientCommand+="\"&&python ";
		//  clientCommand+=clientFilename+"\"";
		  System.out.println(clientCommand);
		
    	Runtime.getRuntime().exec(clientCommand);
    	
    
	}
    
    public void callServerPythonProgram() throws Exception {
    	
    	FileReader reader=new FileReader(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"/strategizer/strategizer.properties");
		  Properties p=new Properties();
		  p.load(reader);
		  String migratorPath = p.getProperty("MigratorPath");
		  Path path = Paths.get(migratorPath);
		  String serverFilename = p.getProperty("ServerPythonProgramName");
		  
		  
		  
		  String serverCommand = "cmd /c start cmd.exe /K \"cd /d \"";
		  serverCommand+= path.toString()+"\"&&python "+serverFilename+"\"";
		  
		  System.out.println(serverCommand);
		  
		  Runtime.getRuntime().exec(serverCommand);
		  
		  
		
  	
	    
    	//Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"cd /d \"C:\\Users\\10673891\\Desktop\\\"&&python server_multiple_reports.py\"");

	    
	}
    
    
	

	
/*	public void callClientPythonProgram() throws Exception {
		
		ProcessBuilder processBuilder = new ProcessBuilder("python", resolvePythonScriptPath("client_1_multiple_reports.py"));
	    processBuilder.redirectErrorStream(true);

	    Process process = processBuilder.start();
	    List<String> results = readProcessOutput(process.getInputStream());
	    
	    System.out.println(results);

	    
	}
	
	public void callServerPythonProgram() throws Exception {
	    ProcessBuilder processBuilder = new ProcessBuilder("python", resolvePythonScriptPath("server_multiple_reports.py"));
	    processBuilder.redirectErrorStream(true);

	    Process process = processBuilder.start();
	    List<String> results = readProcessOutput(process.getInputStream());
	    
	    System.out.println(results);

	    
	}
	private String resolvePythonScriptPath(String filename) {
		
        try
        {
        FileReader reader=new FileReader(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"/strategizer/strategizer.properties");
		  Properties p=new Properties();
		  p.load(reader);
		  String migratorPath = p.getProperty("MigratorPath");
		  Path path = Paths.get(migratorPath+filename);
		  
		  return path.toString();
		  
        }
        catch(Exception e)
        {
        	System.err.println("Failed to create directory!" + e.getMessage());
		    return null;
        }
    }
    
    private static List<String> readProcessOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                .collect(Collectors.toList());
        }
    }*/
	
	
	
	
	
}
