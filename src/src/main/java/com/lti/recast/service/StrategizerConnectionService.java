package com.lti.recast.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.recast.jpa.entity.ProjectReportTargetCon;
import com.lti.recast.jpa.repository.ProjectReportTargetConRepository;
import com.lti.recast.jpa.repository.ReportTypeRepository;
import com.lti.recast.jpa.repository.RptTargetConParamTypeRepository;
import com.lti.recast.util.EntityBuilder;
import com.lti.recast.util.ModelBuilder;
import com.lti.recast.web.model.ProjectReportTargetConModel;
import com.lti.recast.web.model.ReportTypeModel;
import com.lti.recast.web.model.RptTargetConParamTypeModel;

@Service
public class StrategizerConnectionService {
	private static final Logger logger = LoggerFactory.getLogger(StrategizerConnectionService.class);
	
	@Autowired(required = false)
	private ReportTypeRepository reportTypeRepository;
	
	@Autowired(required=false)
	private ProjectReportTargetConRepository projectReportTargetConRepository;
	
	@Autowired(required=false)
	private RptTargetConParamTypeRepository rptTargetConParamTypeRepository;
	
	@Transactional
	public ReportTypeModel getReportTypeByCode(String id) {
		return ModelBuilder.reportTypeModelBuilder(reportTypeRepository.getOne(id));
	}
	
	@Transactional
	public RptTargetConParamTypeModel getRptTargetConParamTypeModelByCode(String code) {
		return ModelBuilder.rptTargetConParamTypeModelBuilder(rptTargetConParamTypeRepository.getOne(code));
	}
	
	@Transactional
	public List<ProjectReportTargetConModel> getConnections(String reportTypeCode) {
		return projectReportTargetConRepository.findByReportTypeCode(reportTypeCode).stream().map(ModelBuilder::projectReportTargetConModelBuilder).collect(Collectors.toList());
	}
	
	public List<RptTargetConParamTypeModel> getParams(String code) {
		return rptTargetConParamTypeRepository.findByReportTypeCode(code).stream().map(ModelBuilder::rptTargetConParamTypeModelBuilder).collect(Collectors.toList());
	}
	
	@Transactional
	public void save(ProjectReportTargetConModel pm) {
		logger.debug("Inside Strategizer Connection Service -> Save");
		pm.setReportType(getReportTypeByCode(pm.getReportType().getCode()));
		pm.getPrjRptTargetConParams().forEach(x -> x.setRptTargetConParamType(getRptTargetConParamTypeModelByCode(x.getRptTargetConParamType().getCode())));
		ProjectReportTargetCon projectReportTargetCon = EntityBuilder.projectReportTargetConEntityBuilder(pm);
		projectReportTargetCon.getPrjRptTargetConParams().forEach(x -> x.setProjectReportTargetCon(projectReportTargetCon));
		projectReportTargetConRepository.save(projectReportTargetCon);
	}
	
	@Transactional
	public String delete(int id) {
		ProjectReportTargetCon p = projectReportTargetConRepository.getOne(id);
		String name = p.getName();
		projectReportTargetConRepository.deleteById(id);
		projectReportTargetConRepository.flush();
		return "Connection: '" + name + "' deleted successfully.";
	}
	
	@Transactional
	public ProjectReportTargetConModel getConnectionDetails(int connectionId) {
		return ModelBuilder.projectReportTargetConModelBuilder(projectReportTargetConRepository.findById(connectionId).get());
	}
	
	
}
