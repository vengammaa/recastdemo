package com.lti.recast.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.recast.jpa.repository.ProjectRptAnalysisRepository;
import com.lti.recast.jpa.repository.ProjectRptMigratorRepository;
import com.lti.recast.jpa.repository.ProjectReportConRepository;
import com.lti.recast.jpa.repository.ReportTypeRepository;
import com.lti.recast.jpa.repository.UniverseReportRepository;
import com.lti.recast.util.ModelBuilder;
import com.lti.recast.web.model.PrjRptAnalysisModel;
import com.lti.recast.web.model.PrjRptMigratorModel;
import com.lti.recast.web.model.PrjRptSourceTaskModel;
import com.lti.recast.web.model.ProjectReportConModel;
import com.lti.recast.web.model.ReportTypeModel;
import com.lti.recast.web.model.UniverseReportModel;
@Service
public class ReportService {
	@Autowired
	private ReportTypeRepository reportRepository;
	@Autowired
	private ProjectReportConRepository projectReportConRepo;
	@Autowired
	private ProjectRptAnalysisRepository analysisRepo;
	
	@Autowired
	private ProjectRptMigratorRepository migratorRepo;
	
	@Autowired(required = false)
	private UniverseReportRepository universeReportRepository;
	
	private Logger logger = LoggerFactory.getLogger(ReportService.class);
	
	@Transactional
	public List<ReportTypeModel> getReportTypes() {
		return reportRepository.findAll().stream().map(ModelBuilder::reportTypeModelBuilder).collect(Collectors.toList());
	}
	
	@Transactional
	public List<ProjectReportConModel> getConnections(int id,String reporttype) {
		return projectReportConRepo.findByIdAndReporttype(id, reporttype).stream().map(ModelBuilder::projectReportConModelBuilder).collect(Collectors.toList());
	}
	
	@Transactional
	public List<PrjRptAnalysisModel> getTasks(int id) {
		logger.info("inside gettasks");
		return analysisRepo.findAllByProjectId(id).stream().map(ModelBuilder::projectReportAnalysisModelBuilder).collect(Collectors.toList());
	}
	
	//Added by Kalpesh for Migrator Code
	@Transactional
	public List<PrjRptMigratorModel> getTasksMigrator(int id) {
		logger.info("inside gettasks");
		return migratorRepo.findAllByProjectId(id).stream().map(ModelBuilder::projectReportMigratorModelBuilder).collect(Collectors.toList());
	}
	
	//Added by Kalpesh for getting universe details for source connections
	@Transactional
	public List<PrjRptAnalysisModel> getSourceTaskUniverse(int projectId, int connectionId) {
		// TODO Auto-generated method stub
		logger.info("inside getSourceTaskUniverse");
		return analysisRepo.findTaskByConnId(projectId,connectionId).stream().map(ModelBuilder::projectReportAnalysisModelBuilder).collect(Collectors.toList());
	}

	
}
