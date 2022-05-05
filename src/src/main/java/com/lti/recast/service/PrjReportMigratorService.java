package com.lti.recast.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.recast.jpa.entity.PrjRptMigrator;
import com.lti.recast.jpa.entity.Project;
import com.lti.recast.jpa.repository.*;
import com.lti.recast.util.EntityBuilder;
import com.lti.recast.util.ModelBuilder;
import com.lti.recast.web.model.PrjRptMigratorModel;




@Service
public class PrjReportMigratorService {

	private static final Logger logger = LoggerFactory.getLogger(PrjReportMigratorService.class);
	
	@Autowired(required = false)
	ProjectRptMigratorRepository projReportMigratorRepo;
	
	@Autowired(required = false)
	private ProjectRepository projectRepository;
	
	@Autowired(required = false)
	private TaskStatusRepository taskStatusRepository;
	
	@Autowired(required = false)
	private PrjRptConParamsRepository prjRptConParamsRepository;
	
	
	@SuppressWarnings("unchecked")
	@Async
	@Transactional
	public CompletableFuture<String> analyzeConnection(PrjRptMigratorModel pm) {
		return null;
		
//		PrjRptMigrator p = projReportMigratorRepo.findById(pm.getId()).get();
//		p.setTaskStatus(taskStatusRepository.findById("INPROG").get());
//		projReportMigratorRepo.saveAndFlush(p);
//		logger.debug("Status of " + p.getTaskName() + " is now set to " + p.getTaskStatus().getName() + ".");
//		
//		List<PrjRptConParams> sourceConParams = prjRptConParamsRepository.findByProjectReportConId(pm.getSourceReportConId());
//		Map<String, String> sourceConn = new HashMap<String, String>();
//		
//		sourceConParams.forEach(x -> sourceConn.put(x.getRptConParamType().getCode(), x.getRptConParamValue()));
//		
//		
//		List<PrjRptConParams> targetConParams = prjRptConParamsRepository.findByProjectReportConId(pm.getTargetReportConId());
//		Map<String, String> targetConn = new HashMap<String, String>();
//		
//		targetConParams.forEach(x -> targetConn.put(x.getRptConParamType().getCode(), x.getRptConParamValue()));
//		
//		
//		try {
//			
//			if(pm.getSourceReportTypeCode().equals(ReportConstants.REPORT_TYPE_COGNOS) && pm.getTargetReportTypeCode().equals(ReportConstants.REPORT_TYPE_MSTR))
//			{
//				/* Set all necessary config parameters */
//				CognosReportConfigDTO cognosConfig = new CognosReportConfigDTO();
//				cognosConfig.setReportType(ReportConstants.REPORT_TYPE_COGNOS);
//				cognosConfig.setHostName(sourceConn.get(CognosConstants.HOST_NAME));
//				cognosConfig.setPort(sourceConn.get(CognosConstants.PORT));
//				cognosConfig.setUsername(sourceConn.get(CognosConstants.USERNAME));
//				cognosConfig.setPassword(sourceConn.get(CognosConstants.PASSWORD));
//				cognosConfig.setNamespace(sourceConn.get(CognosConstants.NAMESPACE));
//				cognosConfig.setPath(sourceConn.get(CognosConstants.PATH));
//				
//				MstrReportConfigDTO mstrConfig = new MstrReportConfigDTO();
//				
//				mstrConfig.setHostname(targetConn.get(MSTRConstants.HOST_NAME));
//				mstrConfig.setUsername(targetConn.get(MSTRConstants.USERNAME));
//				
//				mstrConfig.setPassword(targetConn.get(MSTRConstants.PASSWORD));
//				mstrConfig.setPort(targetConn.get(MSTRConstants.PORT));
//				//CognosReportConvertor migrator = (CognosReportConvertor) ReportConverterFactory.getInstance().getReportAnalyzer((ReportConfigDTO) cognosConfig);
//				CognosReportConvertor migrator = (CognosReportConvertor) ReportConverterFactory.getInstance().getReportAnalyzer((ReportConfigDTO) cognosConfig);
//				
//				String universe = pm.getSourceUniverseName();
//				String universeDesc = pm.getSourceUniverseDesc();
//				String universeMigrationRes = migrator.migrate(universe,universeDesc);
//				System.out.println("Universer Migration:"+universeMigrationRes);
//				
//				if(universeMigrationRes=="SUCCESS")
//				{
//					Map<String,List<String>> reportMap= migrator.migrateReports(universe);
//				}
//				
//			}
//			
//		}
//		
//		catch (Exception e) {
//			p.setTaskStatus(taskStatusRepository.findById("FAIL").get());
//			p.setComment(e.getMessage());
//			projReportMigratorRepo.saveAndFlush(p);
//			logger.debug("Status of " + p.getTaskName() + " is now set to " + p.getTaskStatus().getName() + ".");
//			e.printStackTrace();
//		}
//		return null;
		
		
	}
	
	
	
	@Transactional
	public void deleteTask(int id) {
		logger.info("Deleting : " + id);
		//logger.info(projReportAnalysisRepo.findById(id).get().toString());
	//	commonalityReportRepository.deleteByPrjRptAnalysisId(id);
		projReportMigratorRepo.deleteById(id);
	}
	
	@Transactional
	public PrjRptMigratorModel save(PrjRptMigratorModel pm) {
		logger.debug("Inside report Migrator Service -> Save");
		logger.debug("Setting status as: " + pm.getTaskStatus().getCode());
		Project p = projectRepository.getOne(pm.getProjectId());
		PrjRptMigrator migratorTask = EntityBuilder.prjRptmigratorEntityBuilder(pm);
		migratorTask.setProject(p);
		migratorTask = projReportMigratorRepo.save(migratorTask);
		return ModelBuilder.projectReportMigratorModelBuilder(migratorTask);
	}
	
}
