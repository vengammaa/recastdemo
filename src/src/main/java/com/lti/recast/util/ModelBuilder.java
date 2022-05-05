package com.lti.recast.util;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import com.lti.recast.jpa.entity.AnalysisReport;
import com.lti.recast.jpa.entity.CommonalityReport;
import com.lti.recast.jpa.entity.ComplexityReport;
import com.lti.recast.jpa.entity.FolderTask;
import com.lti.recast.jpa.entity.MigratorStatus;
import com.lti.recast.jpa.entity.PrjRptConParams;
import com.lti.recast.jpa.entity.PrjRptMigrator;
import com.lti.recast.jpa.entity.PrjRptTargetConParams;
import com.lti.recast.jpa.entity.Project;
import com.lti.recast.jpa.entity.ProjectReportCon;
import com.lti.recast.jpa.entity.ProjectReportTargetCon;
import com.lti.recast.jpa.entity.ReportStrategizer;
import com.lti.recast.jpa.entity.PrjRptAnalysis;
import com.lti.recast.jpa.entity.ReportType;
import com.lti.recast.jpa.entity.Role;
import com.lti.recast.jpa.entity.RptConParamType;
import com.lti.recast.jpa.entity.RptTargetConParamType;
import com.lti.recast.jpa.entity.Status;
import com.lti.recast.jpa.entity.StrategizerCalculatedColumn;
import com.lti.recast.jpa.entity.StrategizerCalculations;
import com.lti.recast.jpa.entity.StrategizerDatasourceModel;
import com.lti.recast.jpa.entity.StrategizerMetadataColumns;
import com.lti.recast.jpa.entity.StrategizerQueryConversion;
import com.lti.recast.jpa.entity.StrategizerVisualizationConversion;
import com.lti.recast.jpa.entity.TaskStatus;
import com.lti.recast.jpa.entity.UniverseReport;
import com.lti.recast.jpa.entity.User;
import com.lti.recast.jpa.entity.UserProfile;
import com.lti.recast.tableau.model.ReportData;
import com.lti.recast.web.model.AnalysisReportModel;
import com.lti.recast.web.model.BOTableauMigratorModel;
import com.lti.recast.web.model.CommonalityReportModel;
import com.lti.recast.web.model.ComplexityReportModel;
import com.lti.recast.web.model.MigratorStatusModel;
import com.lti.recast.web.model.PrjRptAnalysisModel;
import com.lti.recast.web.model.PrjRptConParamsModel;
import com.lti.recast.web.model.PrjRptMigratorModel;
import com.lti.recast.web.model.PrjRptTargetConParamsModel;
import com.lti.recast.web.model.ProjectModel;
import com.lti.recast.web.model.ProjectReportConModel;
import com.lti.recast.web.model.ProjectReportTargetConModel;
import com.lti.recast.web.model.QueryColumnModel;
import com.lti.recast.web.model.ReportPathDataModel;
import com.lti.recast.web.model.ReportPathModel;
import com.lti.recast.web.model.ReportStrategizerModel;
import com.lti.recast.web.model.ReportTypeModel;
import com.lti.recast.web.model.RoleModel;
import com.lti.recast.web.model.RptConParamTypeModel;
import com.lti.recast.web.model.RptTargetConParamTypeModel;
import com.lti.recast.web.model.StatusModel;
import com.lti.recast.web.model.StrategizerCalculatedFormulaModel;
import com.lti.recast.web.model.StrategizerCalculationsModel;
import com.lti.recast.web.model.StrategizerDatasourceModelModel;
import com.lti.recast.web.model.StrategizerMetadataColumnModel;
import com.lti.recast.web.model.StrategizerQueryModel;
import com.lti.recast.web.model.StrategizerVisualizationConvertor;
import com.lti.recast.web.model.TaskSelectedReports;
import com.lti.recast.web.model.TaskStatusModel;
import com.lti.recast.web.model.UniverseReportModel;
import com.lti.recast.web.model.UserModel;
import com.lti.recast.web.model.UserProfileModel;

public class ModelBuilder {

	public static UserModel userModelBuilder(User appUser) {
		if (appUser == null) {
			return null;
		}
		UserModel userModel = new UserModel();
		userModel.setUserName(appUser.getUserName());
		userModel.setPassword(appUser.getPassword());
		userModel.setAccountEnabled(appUser.isAccountEnabled());
		userModel.setAccountLocked(appUser.isAccountLocked());
		userModel.setRoles(appUser.getRoles().stream().map(ModelBuilder::roleModelBuilder).collect(Collectors.toList()));
		userModel.setUserProfile(ModelBuilder.userProfileModelBuilder(appUser.getUserProfile()));
		userModel.setProjects(appUser.getProjects().stream().map(x -> x.getName()).collect(Collectors.toList()));
		return userModel;
	}

	public static UserProfileModel userProfileModelBuilder(UserProfile userProfile) {
		if (userProfile == null) {
			return null;
		}
		UserProfileModel userProfileModel = new UserProfileModel();
		userProfileModel.setId(userProfile.getId());
		userProfileModel.setName(userProfile.getName());
		userProfileModel.setEmailid(userProfile.getEmailid());
		userProfileModel.setMobileNo(userProfile.getMobileNo());		
		return userProfileModel;
	}

	public static StatusModel statusModelBuilder(Status e) {
		StatusModel m = new StatusModel();
		m.setCode(e.getCode());
		m.setName(e.getName());
		return m;
	}

	public static RoleModel roleModelBuilder(Role role) {
		RoleModel roleModel = new RoleModel();
		roleModel.setRoleName(role.getRoleName());
		roleModel.setDescription(role.getDescription());
		return roleModel;
	}
	
	public static ProjectModel projectModelBuilder(Project p) {
		ProjectModel pm = new ProjectModel();
		pm.setId(p.getId());
		pm.setName(p.getName());
		pm.setStartDate(p.getStartDate());
		pm.setEndDate(p.getEndDate());
		pm.setStatus(statusModelBuilder(p.getStatus()));
		pm.setUsers(p.getUsers().stream().map(ModelBuilder::userModelBuilder).collect(Collectors.toList()));
		pm.setProjectReportCons(p.getProjectReportCons().stream().map(ModelBuilder::projectReportConModelBuilder).collect(Collectors.toSet()));
		return pm;
	}
	
	public static ReportTypeModel reportTypeModelBuilder(ReportType r) {
		ReportTypeModel rm = new ReportTypeModel();
		rm.setName(r.getName());
		rm.setCode(r.getCode());
		rm.setStatus(statusModelBuilder(r.getStatus()));
		return rm;
	}
	
	public static RptConParamTypeModel rptConParamTypeModelBuilder(RptConParamType r) {
		RptConParamTypeModel rm = new RptConParamTypeModel();
		rm.setCode(r.getCode());
		rm.setName(r.getName());
		rm.setReportType(reportTypeModelBuilder(r.getReportType()));
		return rm;
	}
	
	public static PrjRptConParamsModel prjRptConParamsModelBuilder(PrjRptConParams p) {
		PrjRptConParamsModel pm = new PrjRptConParamsModel();
		pm.setId(p.getId());
		pm.setProjectReportCon(p.getProjectReportCon().getName());
		pm.setRptConParamType(rptConParamTypeModelBuilder(p.getRptConParamType()));
		pm.setRptConParamValue(p.getRptConParamValue());
		return pm;
	}
	
	public static RptTargetConParamTypeModel rptTargetConParamTypeModelBuilder(RptTargetConParamType r) {
		RptTargetConParamTypeModel rm = new RptTargetConParamTypeModel();
		rm.setCode(r.getCode());
		rm.setName(r.getName());
		rm.setReportType(reportTypeModelBuilder(r.getReportType()));
		return rm;
	}
	
	
	
	public static ProjectReportConModel projectReportConModelBuilder(ProjectReportCon p) {
		ProjectReportConModel pm = new ProjectReportConModel();
		pm.setId(p.getId());
		pm.setProject(p.getProject().getName());
		pm.setName(p.getName());
		pm.setReportType(reportTypeModelBuilder(p.getReportType()));
		pm.setStatus(p.getStatus());
		pm.setPrjRptConParams(p.getPrjRptConParams().stream().map(ModelBuilder::prjRptConParamsModelBuilder).collect(Collectors.toSet()));
		return pm;
	}
	
	public static PrjRptTargetConParamsModel prjRptTargetConParamsModelBuilder(PrjRptTargetConParams p) {
		PrjRptTargetConParamsModel pm = new PrjRptTargetConParamsModel();
		pm.setId(p.getId());
		pm.setProjectReportTargetCon(p.getProjectReportTargetCon().getName());
		pm.setRptTargetConParamType(rptTargetConParamTypeModelBuilder(p.getRptTargetConParamType()));
		pm.setRptTargetConParamValue(p.getRptTargetConParamValue());
		return pm;
	}
	
	
	public static ProjectReportTargetConModel projectReportTargetConModelBuilder(ProjectReportTargetCon p) {
		ProjectReportTargetConModel pm = new ProjectReportTargetConModel();
		pm.setId(p.getId());
		pm.setName(p.getName());
		pm.setReportType(reportTypeModelBuilder(p.getReportType()));
		pm.setStatus(p.getStatus());
		pm.setPrjRptTargetConParams(p.getPrjRptTargetConParams().stream().map(ModelBuilder::prjRptTargetConParamsModelBuilder).collect(Collectors.toSet()));
		return pm;
	}
	
	public static ProjectReportTargetConModel projectReportTargetConModelBuilderCopy(ProjectReportTargetCon p) {
		ProjectReportTargetConModel pm = new ProjectReportTargetConModel();
		pm.setId(p.getId());
		pm.setName(p.getName());
//		pm.setReportType(reportTypeModelBuilder(p.getReportType()));
//		pm.setStatus(p.getStatus());
//		pm.setPrjRptTargetConParams(p.getPrjRptTargetConParams().stream().map(ModelBuilder::prjRptTargetConParamsModelBuilder).collect(Collectors.toSet()));
		return pm;
	}
	
	
	public static PrjRptAnalysisModel projectReportAnalysisModelBuilder(PrjRptAnalysis p) {
		PrjRptAnalysisModel pm = new PrjRptAnalysisModel();
		pm.setId(p.getId());
		pm.setProjectId(p.getProject().getId());
		pm.setTaskStatus(taskStatusModelBuilder(p.getTaskStatus()));
		pm.setReportTypeCode(p.getReportTypeCode());
		pm.setProjectReportConId(p.getProjectReportConId());
		pm.setTaskName(p.getTaskName());
		pm.setComment(p.getComment());
		pm.setSelectedReportsList(folderPathModel(p.getTaskFolderdetails()));
		return pm;
	}
	
	private static List<TaskSelectedReports> folderPathModel(List<FolderTask> taskFolderdetails) {
		// TODO Auto-generated method stub
	
		List<TaskSelectedReports> folderTaskList = new ArrayList<TaskSelectedReports>();
		Iterator<FolderTask> folderIterator = taskFolderdetails.iterator();
		
		while(folderIterator.hasNext())
		{
			TaskSelectedReports taskSelectedReportObj = new TaskSelectedReports();
			FolderTask folderTaskObj  = folderIterator.next();
			taskSelectedReportObj.setReportid(folderTaskObj.getReportId());
			taskSelectedReportObj.setReportname(folderTaskObj.getReportName());
			taskSelectedReportObj.setPath(folderTaskObj.getReportPath());
			folderTaskList.add(taskSelectedReportObj);
			
		}
		return folderTaskList;
			
	}

	public static TaskStatusModel taskStatusModelBuilder(TaskStatus t) {
		TaskStatusModel tm = new TaskStatusModel();
		tm.setName(t.getName());
		tm.setCode(t.getCode());
		return tm;
	}
	
	
	public static AnalysisReportModel analysisReportModelBuilder(AnalysisReport ar) {
		AnalysisReportModel am = new AnalysisReportModel();
		am.setId(ar.getId());
		am.setPrjRptAnalysisId(ar.getPrjRptAnalysisId());
		am.setColumnNames(ar.getColumnNames());
		am.setReportId(ar.getReportId());
		am.setReportName(ar.getReportName());
		am.setReportUpdatedDate(ar.getReportUpdatedDate());
		am.setReportCreationDate(ar.getReportCreationDate());
		am.setReportType(ar.getReportType());
		am.setFolderPath(ar.getFolderPath());
		if(ar.getNumberOfInstances()!=null)
		{
			am.setNumberOfInstances(ar.getNumberOfInstances());
		}
		else
		{
			am.setNumberOfInstances(1);
		}
		
		am.setNumberOfInstances(ar.getNumberOfInstances());
		am.setNumberOfRecurringInstances(ar.getNumberOfRecurringInstances());
		am.setAverageRunTime(ar.getAverageRunTime());
		if(ar.getTotalSize()!=null)
		{
			am.setTotalSize(ar.getTotalSize());
		}
		else
		{
			am.setTotalSize(1);
		}
		
		am.setTotalUniverseCount(ar.getTotalUniverseCount());
		am.setNumberOfBlocks(ar.getNumberOfBlocks());
		am.setNumberOfFormulas(ar.getNumberOfFormulas());
		am.setNumberOfFilters(ar.getNumberOfFilters());
		am.setNumberOfTabs(ar.getNumberOfTabs());
		am.setNumberOfColumns(ar.getNumberOfColumns());
		am.setNumberOfQuery(ar.getNumberOfQuery());
		am.setUniverseId(ar.getUniverseId());
		am.setUniverseName(ar.getUniverseName());
		am.setUniversePath(ar.getUniversePath());
		am.setNumberOfFailures(ar.getNumberOfFailures());
		am.setNumberOfRows(ar.getNumberOfRows());
		am.setReportUser(ar.getReportUser());
		am.setCommonalityFactor(ar.getCommonalityFactor());
		am.setTableColumnMap(ar.getTableColumnMap());
		am.setQueryList(ar.getQueryList());
		am.setTableSet(ar.getTableSet());
		am.setChartSet(ar.getChartSet());
		am.setNumberOfReportPages(ar.getNumberOfReportPages());
		am.setNumberOfVariables(ar.getNumberOfVariables());
		am.setNumberOfConditionalBlocks(ar.getNumberOfConditionalBlocks());
		am.setPivotTableSet(ar.getPivotTableSet());
		am.setReportScheduled(ar.isReportScheduled());
		am.setReportPublished(ar.isReportPublished());
		am.setActivelyUsed(ar.isActivelyUsed());
		am.setExceptionReport(ar.getExceptionReport());
		// BO Exclusive
		am.setTabList(ar.getTabList());
		am.setNumberOfImages(ar.getNumberOfImages());
		am.setNumberOfEmbeddedElements(ar.getNumberOfEmbeddedElements());
		am.setComplexity(ar.getComplexity());
		am.setVariableList(ar.getVariableList());
		
		if(ar.getInputControl()!=null)
		{
			am.setInputControl(ar.getInputControl());
		}
		if(ar.getAlerters()!=null)
		{
			am.setAlerters(ar.getAlerters());
		}
		if(ar.getDataFilters()!=null)
		{
			am.setDataFilters(ar.getDataFilters());
		}
		
		if(ar.getQueryFilters()!=null)
		{
			am.setQueryFilters(ar.getQueryFilters());
		}
		
		am.setPageContainer(ar.getPageContainer());
		am.setPageCount(ar.getPageCount());
		am.setContainerCount(ar.getContainerCount());
		am.setPromptCount(ar.getPromptCount());
		am.setPromptPages(ar.getPromptPages());
		
		am.setConditionalBlocks(ar.getConditionalBlocks());
		
		am.setWorkbookName(ar.getWorkbookName());
		
		return am;
	}
	
	public static CommonalityReportModel commonalityReportModelBuilder(CommonalityReport c) {
		CommonalityReportModel cm = new CommonalityReportModel();
		cm.setId(c.getId());
		cm.setPrjRptAnalysisId(c.getPrjRptAnalysisId());
		cm.setAnalysisReport1(analysisReportModelBuilder(c.getAnalysisReport1()));
		cm.setAnalysisReport2(analysisReportModelBuilder(c.getAnalysisReport2()));
		cm.setCommonality(c.getCommonality());
		cm.setIdentical(c.isIdentical());
		return cm;
	}
	
	public static UniverseReportModel universeReportModelBuilder(UniverseReport u) {
		UniverseReportModel um = new UniverseReportModel();
		um.setId(u.getId());
		um.setName(u.getName());
		um.setDescription(u.getDescription());
		um.setUniverseSourceId(u.getUniverseSourceId());
		um.setPrjRptAnalysisId(u.getPrjRptAnalysisId());
		um.setItems(u.getItems());
		um.setTables(u.getTables());
		um.setJoins(u.getJoins());
		um.setDataSources(u.getDataSources());
		um.setConnectionClass(u.getConnectionClass());	
		um.setDbName(u.getDbName());
		return um;
	}
	
	
	//Code added by Kalpesh for Migrator Model builder
	
	public static PrjRptMigratorModel projectReportMigratorModelBuilder(PrjRptMigrator p) {
		PrjRptMigratorModel pm = new PrjRptMigratorModel();
		pm.setId(p.getId());
		pm.setProjectId(p.getProject().getId());
		pm.setTaskStatus(taskStatusModelBuilder(p.getTaskStatus()));
		pm.setSourceReportConId(p.getProjectReportSourceConId());
		pm.setTargetReportConId(p.getProjectReportTargetConId());
		pm.setSourceReportTypeCode(p.getSourceReportTypeCode());
		pm.setTargetReportTypeCode(p.getTargetReportTypeCode());
		pm.setTaskName(p.getTaskName());
		pm.setComment(p.getComment());
		pm.setSourceTaskName(p.getSourceTaskName());
		pm.setSourceUniverseName(p.getSourceUniverseName());
		pm.setSourceUniverseDesc(p.getSourceUniverseDesc());
		return pm;
	}
	
	//Code added by Kalpesh for Universe Details fetch model builder
	
	
//	public static ReportPathModel reportPathModelBuilder(List<ReportData> reportData, String path)
//	{
//		ReportPathModel rpm = new ReportPathModel();
//		List<ReportPathDataModel> list = new LinkedList<ReportPathDataModel>();
//		
//		rpm.setPath(path);
//		
//		Iterator<ReportData> iter = reportData.iterator();
//		
//		while(iter.hasNext())
//		{
//			ReportData rData = iter.next();
//			ReportPathDataModel dataModel = new ReportPathDataModel();
//			
//			dataModel.setDate(rData.getDate());
//			dataModel.setReportId(rData.getReportId());
//			dataModel.setReportName(rData.getReportName());
//			dataModel.setSize(rData.getSize());
//			dataModel.setUniverses(rData.getUniverses());
//			list.add(dataModel);
//		}
//		rpm.setReports(list);
//		
//		
//		return rpm;
//		
//	}
	
	
	public static ReportPathModel reportPathModelBuilderTableau(List<ReportData> reportData, String path)
	{
		ReportPathModel rpm = new ReportPathModel();
		List<ReportPathDataModel> list = new LinkedList<ReportPathDataModel>();
		
		rpm.setPath(path);
		
		Iterator<ReportData> iter = reportData.iterator();
		
		while(iter.hasNext())
		{
			ReportData rData = iter.next();
			ReportPathDataModel dataModel = new ReportPathDataModel();
			
			dataModel.setDate(rData.getUpdatedAt());
			dataModel.setReportId(rData.getId());
			dataModel.setReportName(rData.getName());
			dataModel.setSize(rData.getSize());
			dataModel.setUniverses(rData.getUniverses());
			list.add(dataModel);
		}
		rpm.setReports(list);

		return rpm;
	}

	
	
	public static QueryColumnModel queryColumnModelBuilder(JSONObject querycolumnJSONObj) {
		// TODO Auto-generated method stub
		QueryColumnModel queryColumnModelObj = new QueryColumnModel();
		
		queryColumnModelObj.setObjectId(querycolumnJSONObj.getString("dataSourceObjectId"));
		//queryColumnModelObj.setColumnQualification(querycolumnJSONObj.getString("columnQualification"));
		queryColumnModelObj.setColumnName(querycolumnJSONObj.getString("dataProviderObjectName"));
		
		return queryColumnModelObj;
	
	
	}

	public static StrategizerQueryModel StrategizerQueryBuilder(List<String> selectQueryList) {
		// TODO Auto-generated method stub
		
		StrategizerQueryModel strategizerQueryModel = new StrategizerQueryModel();
		
		//strategizerQueryModel.setDatabaseType("SQL Server database");
		//strategizerQueryModel.setDatabaseName("testdb");
		//strategizerQueryModel.setHostname("10.200.249.19");
		strategizerQueryModel.setDatabaseType("MySQL database");
		strategizerQueryModel.setDatabaseName("efashion");
		strategizerQueryModel.setHostname("localhost");
		
		strategizerQueryModel.setQueryStatement(selectQueryList.get(0));
		strategizerQueryModel.setConvertedQueryStatement(selectQueryList.get(1));
		
		return strategizerQueryModel;
	}

	public static ReportStrategizerModel strategizerModelBuilder(ReportStrategizer strategizerTask) {
		// TODO Auto-generated method stub
		ReportStrategizerModel reportStrategizerModel = new ReportStrategizerModel();
		reportStrategizerModel.setAnalysisTaskId(strategizerTask.getAnalysisTaskId());
		reportStrategizerModel.setTaskName(strategizerTask.getTaskName());	
		reportStrategizerModel.setTaskStatus(taskStatusModelBuilder(strategizerTask.getTaskStatus()));
		reportStrategizerModel.setSourceReportType(strategizerTask.getSourceReportType());
		reportStrategizerModel.setTargetReportType(strategizerTask.getTargetReportType());
		reportStrategizerModel.setId(strategizerTask.getId());
		return reportStrategizerModel;

	}
	
	
	public static StrategizerQueryModel strategizerModelQueryBuider(StrategizerQueryConversion s)
	{
		StrategizerQueryModel strategizerQueryModelObj = new StrategizerQueryModel();
		
		strategizerQueryModelObj.setConvertedQueryName(s.getConvertedQueryName());
		strategizerQueryModelObj.setConvertedQueryStatement(s.getConvertedQueryStatement());
		strategizerQueryModelObj.setDatabaseName(s.getDatabaseName());
		strategizerQueryModelObj.setDatabaseType(s.getDatabaseType());
		strategizerQueryModelObj.setHostname(s.getHostname());
		strategizerQueryModelObj.setQueryName(s.getQueryName());
		strategizerQueryModelObj.setQueryStatement(s.getQueryStatement());
		strategizerQueryModelObj.setReportId(s.getReportId());
		
		return strategizerQueryModelObj;
		
	}
	
	
	public static StrategizerCalculatedFormulaModel strategizerModelCalculatedColumnBuilder(StrategizerCalculatedColumn c)
	{
		StrategizerCalculatedFormulaModel stratFormulaModelObj = new StrategizerCalculatedFormulaModel();
		
		stratFormulaModelObj.setCalculatedFormula(c.getCalculatedFormula());
		stratFormulaModelObj.setColumnQualification(c.getColumnQualification());
		stratFormulaModelObj.setFormula(c.getFormula());
		stratFormulaModelObj.setReportId(c.getReportId());
		stratFormulaModelObj.setReportTabId(c.getReportTabId());
		
		return stratFormulaModelObj;
		
	}
	
	
	public static MigratorStatusModel migratorStatusModelBuilder(MigratorStatus m) 
	{
		MigratorStatusModel migratorStatusObj = new MigratorStatusModel();
		
		migratorStatusObj.setStratTaskId(m.getStratTaskId());
		migratorStatusObj.setReportId(m.getReportId());
		migratorStatusObj.setReportTabId(m.getReportTabId());
		migratorStatusObj.setStatusMessage(m.getStatusMessage());
		migratorStatusObj.setMacroRuntime(m.getMacroRuntime());
		migratorStatusObj.setStartTime(m.getStartTime().toString());
		migratorStatusObj.setEndTime(m.getEndTime().toString());
		
		return migratorStatusObj;
	}
	
	public static StrategizerVisualizationConvertor strategizerModelVisualizationBuilder(StrategizerVisualizationConversion x)
	{
		StrategizerVisualizationConvertor y = new StrategizerVisualizationConvertor();
		
		y.setColor(x.getColor());
		y.setFont(x.getFont());
		y.setFormulaName(x.getFormulaName());
		y.setParentElement(x.getParentElement());
		y.setReportId(x.getReportId());
		y.setReportTabId(x.getReportTabId());
		y.setReportTabName(x.getReportTabName());
		y.setSourceComponentName(x.getSourceComponentName());
		y.setSourceMinimalHeight(x.getSourceMinimalHeight());
		y.setSourceMinimalWidth(x.getSourceMinimalWidth());
		y.setSourcePositionX(x.getSourcePositionX());
		y.setSourcePositionY(x.getSourcePositionY());
		y.setTargetComponentName(x.getTargetComponentName());
		y.setTargetMinimalHeight(x.getTargetMinimalHeight());
		y.setTargetMinimalWidth(x.getTargetMinimalWidth());
		y.setTargetPositionX(x.getTargetPositionX());
		y.setTargetPositionY(x.getTargetPositionY());
		y.setValueType(x.getValueType());
		y.setReportName(x.getReportName());
		y.setCalculations(x.getCalculations());
		y.setSourceElementId(x.getSourceElementId());
		return y;
		
	}

//	public static MigratorModel BOTableauModelBuilder(BOTableauMigratorModel t) {
//		// TODO Auto-generated method stub
//	
//		MigratorModel m = new MigratorModel();
//		m.setStategizerId(t.getStategizerId());
//		
//		List<QueryModel> queryModelList = new LinkedList<QueryModel>();
//		
//		List<StrategizerQueryModel> stratQueryModelList = t.getQueryModelList();
//		
//		stratQueryModelList.forEach(x->{
//			QueryModel q = new QueryModel();
//		//	q.setConvertedQueryName(s1.ge)
//		q.setConvertedQueryName(x.getConvertedQueryName());
//		q.setConvertedQueryStatement(x.getConvertedQueryStatement());
//		q.setDatabaseName(x.getDatabaseName());
//		q.setDatabaseType(x.getDatabaseType());
//		q.setHostname(x.getHostname());
//		q.setQueryName(x.getQueryName());
//		q.setQueryStatement(x.getQueryStatement());
//		q.setReportId(x.getReportId());
//		q.setReportName(x.getReportName());
//		queryModelList.add(q);
//		
//		});
//		m.setQueryModelList(queryModelList);
//		
//		List<StrategizerVisualizationConvertor> visualConvertorList = t.getVisualConvertorList();
//		
//		List<VisualModel> visualModelList = new LinkedList<VisualModel>();
//		
//		
//		
//		visualConvertorList.forEach(x->{
//			
//			VisualModel v = new VisualModel();
//			v.setColor(x.getColor());
//			v.setFont(x.getFont());
//			v.setFormulaName(x.getFormulaName());
//			v.setParentElement(x.getParentElement());
//			v.setReportId(x.getReportId());
//			v.setReportName(x.getReportName());
//			v.setReportTabId(x.getReportTabId());
//			v.setReportTabName(x.getReportTabName());
//			v.setSourceComponentName(x.getSourceComponentName());
//			v.setSourceMinimalHeight(x.getSourceMinimalHeight());
//			v.setSourceMinimalWidth(x.getSourceMinimalWidth());
//			v.setSourcePositionX(x.getSourcePositionX());
//			v.setSourcePositionY(x.getSourcePositionY());
//			v.setTargetComponentName(x.getTargetComponentName());
//			v.setTargetMinimalHeight(x.getTargetMinimalHeight());
//			v.setTargetMinimalWidth(x.getTargetMinimalWidth());
//			v.setTargetPositionX(x.getTargetPositionX());
//			v.setTargetPositionY(x.getTargetPositionY());
//			v.setValueType(x.getValueType());
//			v.setCalculations(x.getCalculations());
//			visualModelList.add(v);
//		});
//		
//		m.setVisualModelList(visualModelList);
//		
//		
//		List<CalculatedFormulaModel> calculatedModelList = new LinkedList<CalculatedFormulaModel>();
//		
//		List<StrategizerCalculatedFormulaModel> strategizerCalculatedFormulaList = t.getCalculatedFormulaList();
//		
//		strategizerCalculatedFormulaList.forEach(x->{
//			
//			CalculatedFormulaModel c = new CalculatedFormulaModel();
//			
//			c.setCalculatedFormula(x.getCalculatedFormula());
//			c.setColumnQualification(x.getColumnQualification());
//			c.setFormula(x.getFormula());
//			c.setReportId(x.getReportId());
//			c.setReportName(x.getReportName());
//			c.setReportTabId(x.getReportTabId());
//			
//			calculatedModelList.add(c);
//		});
//		m.setCalculatedFormualaModelList(calculatedModelList);
//		
//		List<StrategizerMetadataColumnModel> strategizerMetadataColumnModelList = t.getMetadataColumnList();
//		
//		List<MetadataColumnModel> metadataColumnList = new LinkedList<MetadataColumnModel>();
//		
//		strategizerMetadataColumnModelList.forEach(x->{
//			MetadataColumnModel c = new MetadataColumnModel();
//			
//			c.setDatatype(x.getDatatype());
//			c.setMetadataColumnName(x.getMetadataColumnName());
//			c.setReportId(x.getReportId());
//			c.setReportName(x.getReportName());
//			c.setSemanticsType(x.getSemanticsType());
//			c.setTableName(x.getTableName());
//			c.setValueType(x.getValueType());
//			metadataColumnList.add(c);
//			
//			
//		});
//		
//		m.setMetadataColumnModelList(metadataColumnList);
//		
//		List<DatasourceModel> dataSourceModelList = new LinkedList<DatasourceModel>();
//		
//		List<StrategizerDatasourceModelModel> stratDataModelList = t.getDatasourceModelList();
//		
//		stratDataModelList.forEach(x->{
//			
//			DatasourceModel c = new DatasourceModel();
//			
//			c.setLcolumn(x.getLcolumn());
//			c.setLtable(x.getLtable());
//			c.setRcolumn(x.getRcolumn());
//			c.setReportId(x.getReportId());
//			c.setRtable(x.getRtable());
//			c.setType(x.getType());
//			dataSourceModelList.add(c);
//			
//		});
//		m.setDatasourceModelList(dataSourceModelList);
//		
//		List<CalculationsModel> calculationsList = new LinkedList<CalculationsModel>();
//		
//		List<StrategizerCalculationsModel> stratCalculationsList = t.getCalculationsList();
//		
//		stratCalculationsList.forEach(x->{
//			CalculationsModel c = new CalculationsModel();
//			c.setCalculationName(x.getCalculationName());
//			c.setColumnNames(x.getColumnNames());
//			c.setFormula(x.getFormula());
//			c.setReportId(x.getReportId());
//			c.setReportName(x.getReportName());
//			calculationsList.add(c);
//		});
//		m.setCalculationsList(calculationsList);
//		
//		return m;
//	}
	
	
	public static StrategizerMetadataColumnModel strategizerModelMetadataColumnBuilder(StrategizerMetadataColumns c)
	{
		StrategizerMetadataColumnModel stratMetadataColumnModelObj = new StrategizerMetadataColumnModel();
		
		stratMetadataColumnModelObj.setDatatype(c.getDatatype());
		stratMetadataColumnModelObj.setMetadataColumnName(c.getMetadataColumnName());
		stratMetadataColumnModelObj.setReportId(c.getReportId());
		stratMetadataColumnModelObj.setReportName(c.getReportName());
		stratMetadataColumnModelObj.setSemanticsType(c.getSemanticsType());
		stratMetadataColumnModelObj.setTableName(c.getTableName());
		stratMetadataColumnModelObj.setValueType(c.getValueType());
		return stratMetadataColumnModelObj;
		
	}
	
	
	public static StrategizerDatasourceModelModel strategizerModelDataModelBuilder(StrategizerDatasourceModel c)
	{
		StrategizerDatasourceModelModel stratDatasourceModelObj = new StrategizerDatasourceModelModel();
		
		stratDatasourceModelObj.setLcolumn(c.getLcolumn());
		stratDatasourceModelObj.setLtable(c.getLtable());
		stratDatasourceModelObj.setRcolumn(c.getRcolumn());
		stratDatasourceModelObj.setReportId(c.getReportId());
		stratDatasourceModelObj.setRtable(c.getRtable());
		stratDatasourceModelObj.setType(c.getType());
		
		
		return stratDatasourceModelObj;
		
	}
	
	public static StrategizerCalculationsModel strategizerCalculationsModelBuilder(StrategizerCalculations c)
	{
		StrategizerCalculationsModel stratCalculationsModelObj = new StrategizerCalculationsModel();

		stratCalculationsModelObj.setCalculationName(c.getCalculationName());
		stratCalculationsModelObj.setColumnNames(c.getColumnNames());
		stratCalculationsModelObj.setFormula(c.getFormula());
		stratCalculationsModelObj.setReportId(c.getReportId());
		stratCalculationsModelObj.setReportName(c.getReportName());
		
		return stratCalculationsModelObj;
		
	}

	public static PrjRptAnalysisModel projectReportAnalysisModelBuilderCognos(PrjRptAnalysis p) {
		// TODO Auto-generated method stub
		PrjRptAnalysisModel pm = new PrjRptAnalysisModel();
		pm.setId(p.getId());
		pm.setProjectId(p.getProject().getId());
		pm.setTaskStatus(taskStatusModelBuilder(p.getTaskStatus()));
		pm.setReportTypeCode(p.getReportTypeCode());
		pm.setProjectReportConId(p.getProjectReportConId());
		pm.setTaskName(p.getTaskName());
		pm.setComment(p.getComment());
		//pm.setSelectedReportsList(folderPathModel(p.getTaskFolderdetails()));
		return pm;
	}
	
	public static ComplexityReportModel complexityReportModelBuilder(ComplexityReport c) {
		ComplexityReportModel cm = new ComplexityReportModel();
		
		cm.setComplexityId(c.getComplexityId());
		cm.setComplexityParameter(c.getComplexityParameter());
		cm.setComplexityStatus(c.getComplexityStatus());
		cm.setReportId(c.getReportId());
		cm.setReportName(c.getReportName());
		cm.setTaskId(c.getTaskId());
		
		return cm;
	}

}
