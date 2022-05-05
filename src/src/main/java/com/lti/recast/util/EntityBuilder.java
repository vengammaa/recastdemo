package com.lti.recast.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.lti.recast.jpa.entity.AnalysisReport;
import com.lti.recast.jpa.entity.AnalysisReportsTable;
import com.lti.recast.jpa.entity.AnalysisSemanticColumns;
import com.lti.recast.jpa.entity.CommonalityParams;
import com.lti.recast.jpa.entity.CommonalityReport;

import com.lti.recast.jpa.entity.ComplexityReport;
import com.lti.recast.jpa.entity.FolderTask;
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
import com.lti.recast.jpa.entity.StrategizerQueryConversion;
import com.lti.recast.jpa.entity.StrategizerVisualizationConversion;
import com.lti.recast.jpa.entity.TaskStatus;
import com.lti.recast.jpa.entity.UniverseReport;
import com.lti.recast.jpa.entity.User;
import com.lti.recast.jpa.entity.UserProfile;
import com.lti.recast.jpa.entity.VisualDetails;
import com.lti.recast.tableau.model.TableauAnalyzerModel;
import com.lti.recast.tableau.model.TableauSemanticModel;
import com.lti.recast.tableau.model.TableauVisualElements;
import com.lti.recast.web.model.AnalysisReportModel;
import com.lti.recast.web.model.CommonalityReportModel;
import com.lti.recast.web.model.PrjRptAnalysisModel;
import com.lti.recast.web.model.PrjRptConParamsModel;
import com.lti.recast.web.model.PrjRptMigratorModel;
import com.lti.recast.web.model.PrjRptTargetConParamsModel;
import com.lti.recast.web.model.ProjectModel;
import com.lti.recast.web.model.ProjectReportConModel;
import com.lti.recast.web.model.ProjectReportTargetConModel;
import com.lti.recast.web.model.ReportStrategizerModel;
import com.lti.recast.web.model.ReportTypeModel;
import com.lti.recast.web.model.RoleModel;
import com.lti.recast.web.model.RptConParamTypeModel;
import com.lti.recast.web.model.RptTargetConParamTypeModel;
import com.lti.recast.web.model.StatusModel;
import com.lti.recast.web.model.StrategizerCalculatedFormulaModel;
import com.lti.recast.web.model.StrategizerQueryModel;
import com.lti.recast.web.model.StrategizerVisualizationConvertor;
import com.lti.recast.web.model.TaskSelectedReports;
import com.lti.recast.web.model.TaskStatusModel;
import com.lti.recast.web.model.UserModel;
import com.lti.recast.web.model.UserProfileModel;

public class EntityBuilder {

	public static User userEntityBuilder(UserModel userModel) {
		User user = new User();
		user.setUserName(userModel.getUserName());
		user.setPassword(userModel.getPassword());
		user.setAccountEnabled(userModel.isAccountEnabled());
		user.setAccountLocked(userModel.isAccountLocked());	
		user.setRoles(userModel.getRoles().stream().map(EntityBuilder::roleEntityBuilder).collect(Collectors.toSet()));
		user.setUserProfile(userProfileEntityBuilder(userModel.getUserProfile()));
		//user.setProjects(userModel.getProjects().stream().map(EntityBuilder::projectEntityBuilder).collect(Collectors.toSet()));
		return user;
	}
	
	public static UserProfile userProfileEntityBuilder(UserProfileModel userProfileModel) {
		UserProfile userProfile = new UserProfile();
		userProfile.setName(userProfileModel.getName());
		userProfile.setEmailid(userProfileModel.getEmailid());
		userProfile.setMobileNo(userProfileModel.getMobileNo());
		return userProfile;
	}
	
	public static Role roleEntityBuilder(RoleModel roleModel) {
		Role role = new Role();
		role.setRoleName(roleModel.getRoleName());
		role.setDescription(roleModel.getDescription());
		return role;
	}
	
	
	public static Project projectEntityBuilder(ProjectModel projectModel) {
		Project project = new Project();
		project.setId(projectModel.getId());
		project.setStatus(statusEntityBuilder(projectModel.getStatus()));
		project.setName(projectModel.getName());
		project.setStartDate(projectModel.getStartDate());
		project.setEndDate(projectModel.getEndDate());
		project.setUsers(projectModel.getUsers().stream().map(EntityBuilder::userEntityBuilder).collect(Collectors.toSet()));
		project.setProjectReportCons(projectModel.getProjectReportCons().stream().map(EntityBuilder::projectReportConEntityBuilder).collect(Collectors.toSet()));
		return project;
	}
	
	
	public static Status statusEntityBuilder(StatusModel statusModel) {
		Status status = new Status();
		status.setName(statusModel.getName());
		status.setCode(statusModel.getCode());
		return status;
	}
	

	public static ProjectReportCon projectReportConEntityBuilder(ProjectReportConModel pm) {
		ProjectReportCon p = new ProjectReportCon();
		p.setReportType(reportTypeEntityBuilder(pm.getReportType()));
		p.setName(pm.getName());
		p.setPrjRptConParams(pm.getPrjRptConParams().stream().map(EntityBuilder::prjRptConParamsEntityBuilder).collect(Collectors.toSet()));
		p.setStatus(pm.getStatus());
		return p;
	}
	
	
	public static ProjectReportTargetCon projectReportTargetConEntityBuilder(ProjectReportTargetConModel pm) {
		System.out.println("Project ReportTarget ConModel:: " + pm);
		ProjectReportTargetCon p = new ProjectReportTargetCon();
		p.setReportType(reportTypeEntityBuilder(pm.getReportType()));
		p.setName(pm.getName());
		p.setPrjRptTargetConParams(pm.getPrjRptTargetConParams().stream().map(EntityBuilder::prjRptTargetConParamsEntityBuilder).collect(Collectors.toSet()));
		System.out.println("\nProject Report Method::" + p);
		return p;
	}

	public static ProjectReportTargetCon projectReportTargetConEntityBuilderCopy(ProjectReportTargetConModel pm) {
		System.out.println("Project ReportTarget ConModel:: " + pm);
		ProjectReportTargetCon p = new ProjectReportTargetCon();
		p.setId(pm.getId());
//		p.setReportType(reportTypeEntityBuilder(pm.getReportType()));
		p.setName(pm.getName());
//		p.setPrjRptTargetConParams(pm.getPrjRptTargetConParams().stream().map(EntityBuilder::prjRptTargetConParamsEntityBuilder).collect(Collectors.toSet()));
		System.out.println("\nProject Report Method::" + p);
		return p;
	}
	
	
	
	public static ReportType reportTypeEntityBuilder(ReportTypeModel rm) {
		ReportType r = new ReportType();
		r.setName(rm.getName());
		r.setCode(rm.getCode());
		r.setStatus(statusEntityBuilder(rm.getStatus()));
		return r;
	}

	public static RptConParamType rptConParamTypeEntityBuilder(RptConParamTypeModel rm) {
		RptConParamType r = new RptConParamType();
		r.setCode(rm.getCode());
		r.setName(rm.getName());
		r.setReportType(reportTypeEntityBuilder(rm.getReportType()));
		return r;
	}
	
	public static PrjRptConParams prjRptConParamsEntityBuilder(PrjRptConParamsModel pm) {
		PrjRptConParams p = new PrjRptConParams();
		p.setRptConParamType(rptConParamTypeEntityBuilder(pm.getRptConParamType()));
		p.setRptConParamValue(pm.getRptConParamValue());
		return p;	
	}
	
	public static RptTargetConParamType rptTargetConParamTypeEntityBuilder(RptTargetConParamTypeModel rm) {
		System.out.println("RptTargetConParamModel::" + rm);
		RptTargetConParamType r = new RptTargetConParamType();
		r.setCode(rm.getCode());
		r.setName(rm.getName());
		r.setReportType(reportTypeEntityBuilder(rm.getReportType()));
		return r;
	}
	
	
	public static PrjRptTargetConParams prjRptTargetConParamsEntityBuilder(PrjRptTargetConParamsModel pm) {
		System.out.println("Prj Rpt Target Con Params::" + pm);
		PrjRptTargetConParams p = new PrjRptTargetConParams();
		p.setRptTargetConParamType(rptTargetConParamTypeEntityBuilder(pm.getRptTargetConParamType()));
		p.setRptTargetConParamValue(pm.getRptTargetConParamValue());
		return p;
	}
	
	
	public static PrjRptAnalysis prjRptanalysisEntityBuilder(PrjRptAnalysisModel pm) {
		PrjRptAnalysis p = new PrjRptAnalysis();
		p.setReportTypeCode(pm.getReportTypeCode());
		p.setProjectReportConId(pm.getProjectReportConId());
		p.setTaskName(pm.getTaskName());
		p.setTaskStatus(taskStatusEntityBuilder(pm.getTaskStatus()));
		p.setComment(pm.getComment());
	//	p.setTaskFolderdetails(taskFolderEntityBuilder(pm.getSelectedReportsList()));
		return p;	
	}
	
	

	public static TaskStatus taskStatusEntityBuilder(TaskStatusModel tm) {
		TaskStatus t = new TaskStatus();
		t.setCode(tm.getCode());
		t.setName(tm.getName());
		return t;
		
	}
	
	private static List<FolderTask> taskFolderEntityBuilder(List<TaskSelectedReports> selectedReportsList) {
		
		List<FolderTask> folderTaskList = new ArrayList<FolderTask>();
		Iterator<TaskSelectedReports> folderIterator = selectedReportsList.iterator();
		
		while(folderIterator.hasNext())
		{
			FolderTask folderTaskObj = new FolderTask();
			TaskSelectedReports taskSelectedReportObj = folderIterator.next();
			folderTaskObj.setReportId(taskSelectedReportObj.getReportid());
			folderTaskObj.setReportName(taskSelectedReportObj.getReportname());
			folderTaskObj.setReportPath(taskSelectedReportObj.getPath());
			folderTaskList.add(folderTaskObj);
			
		}
		return folderTaskList;
	}
	
	
	public static AnalysisReport analysisReportEntityBuilder(AnalysisReportModel am) {
		AnalysisReport analysis = new AnalysisReport();
		analysis.setPrjRptAnalysisId(am.getPrjRptAnalysisId());
		analysis.setColumnNames(am.getColumnNames());
		analysis.setReportType(am.getReportType());
		analysis.setReportId(String.valueOf(am.getReportId()));
		analysis.setReportName(am.getReportName());
		analysis.setReportUpdatedDate(am.getReportUpdatedDate());
		analysis.setReportCreationDate(am.getReportCreationDate());
		analysis.setFolderPath(am.getFolderPath());
		analysis.setNumberOfInstances(am.getNumberOfInstances());
		analysis.setNumberOfRecurringInstances(am.getNumberOfRecurringInstances());
		analysis.setAverageRunTime(am.getAverageRunTime());
		analysis.setTotalSize(am.getTotalSize());
		analysis.setTotalUniverseCount(am.getTotalUniverseCount());
		analysis.setNumberOfBlocks(am.getNumberOfBlocks());
		analysis.setNumberOfFormulas(am.getNumberOfFormulas());
		analysis.setNumberOfTabs(am.getNumberOfTabs());
		analysis.setNumberOfFilters(am.getNumberOfFilters());
		analysis.setNumberOfColumns(am.getNumberOfColumns());
		analysis.setNumberOfQuery(am.getNumberOfQuery());
		analysis.setUniverseName(am.getUniverseName());
		analysis.setUniversePath(am.getUniversePath());
		analysis.setUniverseId(am.getUniverseId());
		analysis.setReportScheduled(am.isReportScheduled());
		analysis.setNumberOfRows(am.getNumberOfRows());
		analysis.setActivelyUsed(am.isActivelyUsed());
		analysis.setNumberOfFailures(am.getNumberOfFailures());
		analysis.setReportUser(am.getReportUser());
		analysis.setReportPublished(am.isReportPublished());
		analysis.setCommonalityFactor(am.getCommonalityFactor());
		analysis.setTableColumnMap(am.getTableColumnMap());
		analysis.setQueryList(am.getQueryList());
		analysis.setTableSet(am.getTableSet());
		analysis.setChartSet(am.getChartSet());
		analysis.setNumberOfReportPages(am.getNumberOfReportPages());
		analysis.setNumberOfVariables(am.getNumberOfVariables());
		analysis.setNumberOfConditionalBlocks(am.getNumberOfConditionalBlocks());
		analysis.setPivotTableSet(am.getPivotTableSet());
		
		return analysis;
		
	}
	
//	public static AnalysisReport analysisReportEntityBuilder(CognosAnalyzerModel cm) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss zzz yyyy");
//		AnalysisReport a = new AnalysisReport();
//		a.setColumnNames(cm.getColumnNames().toString());
//		a.setReportType(cm.getReportType());
//		a.setReportId(String.valueOf(cm.getReportId()));
//		a.setReportName(cm.getReportName());
//		a.setReportUpdatedDate(cm.getReportUpdatedDate());
//		a.setReportCreationDate(LocalDate.parse(cm.getReportCreationDate(), formatter));
//		a.setFolderPath(cm.getFolderPath());
//		a.setNumberOfInstances(cm.getNumberOfInstances());
//		a.setNumberOfRecurringInstances(cm.getNumberOfRecurringInstances());
//		a.setAverageRunTime(cm.getAverageRunTime());
//		a.setTotalSize(cm.getTotalSize());
//		a.setTotalUniverseCount(cm.getTotalUniverseCount());
//		a.setNumberOfBlocks(cm.getNumberOfBlocks());
//		a.setNumberOfFormulas(cm.getNumberOfFormulas());
//		a.setNumberOfTabs(cm.getNumberOfTabs());
//		a.setNumberOfFilters(cm.getNumberOfFilters());
//		a.setNumberOfColumns(cm.getNumberOfColumns());
//		a.setNumberOfQuery(cm.getNumberOfQuery());
//		a.setUniverseName(cm.getUniverseName());
//		a.setUniversePath(cm.getUniversePath());
//		a.setUniverseId(cm.getUniverseId());
//		a.setReportScheduled(cm.getIsReportScheduled());
//		a.setNumberOfRows(cm.getNumberOfRows());
//		a.setActivelyUsed(cm.isActivelyUsed());
//		a.setNumberOfFailures(cm.getNumberOfFailures());
//		a.setReportUser(cm.getReportUser());
//		a.setReportPublished(cm.getIsReportPublished());
//		a.setCommonalityFactor(cm.getCommonalityFactor());
//		a.setTableColumnMap(cm.getTableColumnMapJSON());
//		a.setQueryList(cm.getQueryListJSON());
//		a.setTableSet(cm.getTableSetJSON());
//		a.setChartSet(cm.getChartSetJSON());
//		a.setNumberOfReportPages(cm.getNoOfReportPages()); 
//		a.setNumberOfVariables(cm.getNoOfVariables());
//		a.setNumberOfConditionalBlocks(cm.getNoOfConditionalBlocks());
//		a.setPivotTableSet(cm.getPivotTableSet().toString());
//		a.setExceptionReport(cm.getExceptionReport());	
//		a.setPageContainer(cm.getPageContainerJSON());
//		a.setPageCount(cm.getPageCount());
//		a.setContainerCount(cm.getContainerCount());
//		a.setPromptPages(cm.getPromptPagesJSON());
//		a.setPromptCount(cm.getPromptCount());
//		a.setConditionalBlocks(cm.getPageConditionalBlockJSON());
//		return a;
//	}
	
//	public static AnalysisReport analysisReportEntityBuilder(BOAnalyzerModel bm) {
//		
//		System.out.println("BO Model::"+bm);
//		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
//		AnalysisReport a = new AnalysisReport();
//		a.setColumnNames(bm.getColumnNameList().toString());
//		a.setReportId(String.valueOf(bm.getReportId()));
//		a.setReportName(bm.getReportName());
//		a.setReportType(bm.getReportType());
//		a.setFolderPath(bm.getReportFolderPath());
//		a.setReportUpdatedDate(bm.getReportUpdatedDate());
//		a.setReportScheduled(bm.getIsReportScheduled());
//		a.setTotalSize((int)Math.round(bm.getReportSize()));	
//		a.setReportUser(bm.getReportUser());
//		a.setNumberOfBlocks(bm.getNumberOfBlocks());
//		a.setNumberOfFormulas(bm.getNumberOfFormulas());
//		a.setNumberOfTabs(bm.getNumberOfTabs());
//		a.setNumberOfFilters(bm.getNumberOfFilters());
//		a.setNumberOfColumns(bm.getNumberOfColumns());
//		a.setNumberOfQuery(bm.getNumberOfQuery());
//		a.setTotalUniverseCount(bm.getTotalUniverseCount());
//		a.setUniverseName(String.join(", ", bm.getUniverses()));
//		a.setActivelyUsed(bm.getActivelyUsed());
//		a.setNumberOfInstances(bm.getNumberOfInstances());
//		a.setComplexity((double) Math.round(bm.getReportComplexity()));
//		a.setNumberOfRecurringInstances(bm.getNumberOfRecurringInstances());
//		a.setNumberOfFailures(bm.getNumberOfFailures());
//		a.setTabList(bm.getBOReportTabListJSON());
//		a.setQueryList(bm.getBoReportQueriesJSON());
//		a.setCommonalityFactor(bm.getCommonalityFactor());
//		a.setNumberOfImages(bm.getNumberOfImages());
//		a.setNumberOfEmbeddedElements(bm.getNumberOfEmbeddedElements());
//		a.setVariableList(bm.getBOReportVariableListJSON());
//		a.setExceptionReport(bm.getExceptionReport());
//		a.setInputControl(bm.getInputControlListJSON());
//		a.setAlerters(bm.getAlertersListJSON());
//		a.setDataFilters(bm.getDataFiltersListJSON());
//		
//		a.setPromptCount(0);
//		
//		return a;
//	}
	
	
	public static AnalysisReport analysisReportEntityBuilder(TableauAnalyzerModel tm) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
		AnalysisReport a = new AnalysisReport();
		a.setColumnNames(tm.getColumnNameList().toString());
		a.setReportId(String.valueOf(tm.getReportId()));
		a.setReportName(tm.getReportName());
		a.setReportType(tm.getReportType());
		a.setFolderPath(tm.getReportFolderPath());
		if(tm.getReportUpdatedDate()!=null)
		{
			a.setReportUpdatedDate(tm.getReportUpdatedDate());
		}
		
		a.setReportScheduled(tm.getIsReportScheduled());
		if(tm.getReportSize()!=null)
		{
			a.setTotalSize((int)Math.round(tm.getReportSize()));	
		}
		
		a.setReportUser(tm.getReportUser());
		a.setNumberOfBlocks(tm.getNumberOfBlocks());
		a.setNumberOfFormulas(tm.getNumberOfFormulas());
		a.setNumberOfTabs(tm.getNumberOfTabs());
		a.setNumberOfFilters(tm.getNumberOfFilters());
		a.setNumberOfColumns(tm.getNumberOfColumns());
		a.setNumberOfQuery(tm.getNumberOfQuery());
		a.setTotalUniverseCount(tm.getTotalUniverseCount());
		if(tm.getUniverses()!=null)
		{
			a.setUniverseName(String.join(", ", tm.getUniverses()));
		}
		
		a.setActivelyUsed(tm.getActivelyUsed());
		a.setNumberOfInstances(tm.getNumberOfInstances());
		if(tm.getReportComplexity()!=null)
		{
			a.setComplexity((double) Math.round(tm.getReportComplexity()));
		}
	
		a.setNumberOfRecurringInstances(tm.getNumberOfRecurringInstances());
		a.setNumberOfFailures(tm.getNumberOfFailures());
		a.setTabList(tm.getTableauReportTabListJSON());
		a.setQueryList(tm.getTableauReportQueriesJSON());
		a.setCommonalityFactor(tm.getCommonalityFactor());
		a.setNumberOfImages(tm.getNumberOfImages());
		a.setNumberOfEmbeddedElements(tm.getNumberOfEmbeddedElements());
		a.setVariableList(tm.getTableauReportVariableListJSON());
		a.setExceptionReport(tm.getExceptionReport());
		
		a.setWorkbookName(tm.getWorkbookName());	
		a.setPromptCount(0);
		return a;
	}
	
	
	public static CommonalityReport commonalityReportEntityBuilder(CommonalityReportModel cm) {
		CommonalityReport c = new CommonalityReport();
		c.setId(cm.getId());
		c.setAnalysisReport1(analysisReportEntityBuilder(cm.getAnalysisReport1()));
		c.setAnalysisReport2(analysisReportEntityBuilder(cm.getAnalysisReport2()));
		c.setCommonality(cm.getCommonality());
		c.setIdentical(c.isIdentical());
		c.setPrjRptAnalysisId(c.getPrjRptAnalysisId());
		return c;
	}
	
//	public static CommonalityReport commonalityReportEntityBuilder(CognosCommonalityModel cm) {
//		CommonalityReport c = new CommonalityReport();
//		c.setAnalysisReport1(analysisReportEntityBuilder(cm.getCognosAnalyzerModel1()));
//		c.setAnalysisReport2(analysisReportEntityBuilder(cm.getCognosAnalyzerModel2()));
//		c.setCommonality(cm.getCommonality());
//		c.setIdentical(cm.getIdentical());
//		return c;
//	}
	
//	public static CommonalityReport commonalityReportEntityBuilder(BOCommonalityModel bm) {
//		CommonalityReport c = new CommonalityReport();
//		c.setAnalysisReport1(analysisReportEntityBuilder(bm.getBoAnalyzerModel1()));
//		c.setAnalysisReport2(analysisReportEntityBuilder(bm.getBoAnalyzerModer2()));
//		c.setCommonality(bm.getCommonality());
//		c.setIdentical(bm.getIdentical());
//		return c;
//	}
	
//	public static UniverseReport universeReportEntityBuilder(BOUniverse b) {
//		UniverseReport u = new UniverseReport();
//		u.setName(b.getName());
//		u.setUniverseSourceId(Integer.toString(b.getId()));
//		u.setDescription(b.getDescription());
//		u.setItems(b.getBOItemsJSON());
//		u.setTables(b.getTablesJSON());
//		u.setJoins(b.getJoinsJSON());
//		return u;
//	}
	
//	public static UniverseReport universeReportEntityBuilder(CognosSemanticLayer c) {
//		
//		UniverseReport u = new UniverseReport();
//		u.setName(c.getName());
//		u.setDescription(c.getPath());
//		u.setItems(c.getCognosItemsJSON());
//		u.setTables(c.getTablesJSON());
//		u.setJoins(c.getJoinsJSON());
//		u.setDataSources(c.getDataSourceJSON());
//		
//		return u;
//	}
	
	public static UniverseReport universeReportEntityBuilder(TableauSemanticModel t) {
		UniverseReport u = new UniverseReport();
		u.setName(t.getName());
		u.setConnectionClass(t.getConnectionClass());
		u.setDescription(t.getDescription());
		u.setTables(t.getTablesJSON());
		u.setItems(t.getTableauItemsJSON());
		u.setJoins(" ");
		u.setFilters(t.getTableauFiltersJSON());
		u.setDbName(t.getDbName());
		
		return u;
	}
	
	
	public static PrjRptMigrator prjRptmigratorEntityBuilder(PrjRptMigratorModel pm) {
		PrjRptMigrator p = new PrjRptMigrator();
		p.setSourceReportTypeCode(pm.getSourceReportTypeCode());
		p.setProjectReportSourceConId(pm.getSourceReportConId());
		p.setTargetReportTypeCode(pm.getTargetReportTypeCode());
		p.setProjectReportTargetConId(pm.getTargetReportConId());
		p.setTaskName(pm.getTaskName());
		p.setTaskStatus(taskStatusEntityBuilder(pm.getTaskStatus()));
		p.setComment(pm.getComment());
		p.setSourceTaskName(pm.getSourceTaskName());
		p.setSourceUniverseName(pm.getSourceUniverseName());
		p.setSourceUniverseDesc(pm.getSourceUniverseDesc());
		return p;	
	}
	
	public static void main(String args[]) throws Exception {
		String s = "2020-08-13T18:06:06.524Z";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");
		LocalDate z = LocalDate.parse(s, formatter);
		System.out.print(z);
			
	}

	public static FolderTask folderReportEntityBuilder(TaskSelectedReports x) {
		// TODO Auto-generated method stub
		
		FolderTask folderTaskObj = new FolderTask();
		
		folderTaskObj.setReportId(x.getReportid());
		folderTaskObj.setReportName(x.getReportname());
		folderTaskObj.setReportPath(x.getPath());
		
		return folderTaskObj;
	}

	/*public static ComplexityDetails complexityDetailsEntityBuilder(BONewReportComplexity v) {
		// TODO Auto-generated method stub
		
		ComplexityDetails complexityDetails = new ComplexityDetails();
		
		complexityDetails.setReportId(v.getReportId());
		complexityDetails.setReportName(v.getReportName());
		complexityDetails.setNumberOfDataSources(v.getNumberOfDataSources());
		complexityDetails.setNumberOfQueries(v.getNumberOfQueries());
		complexityDetails.setNumberOfReportElements(v.getNumberOfReportElements());
		complexityDetails.setNumberOfReportTabs(v.getNumberOfReportTabs());
		complexityDetails.setNumberOfVariables(v.getNumberOfVariables());
		complexityDetails.setNumberOfObjects(v.getNumberOfObjects());
		
		return complexityDetails;
		
	}*/

//	public static AnalysisReportsTable analysisReportTableEnityBuilder(BOQueryDetailsModel x) {
//		// TODO Auto-generated method stub
//		
//		AnalysisReportsTable a = new AnalysisReportsTable();
//		a.setReportId(String.valueOf(x.getReportId()));
//		a.setReportName(x.getReportName());
//		a.setDataSource(x.getDataSourceName());
//		a.setQueryName(x.getQueryName());
//		a.setTableAliasNames(x.getTableAliasName());
//		a.setTableNames(x.getTableName());
//		
//		return a;
//		
//		
//	}

//	public static VisualDetails visualDetailsEntityBuider(BOVisualElements z) {
//		// TODO Auto-generated method stub
//		
//		VisualDetails visualDetails = new VisualDetails();
//		
//		visualDetails.setAllignment(z.getAllignment());
//		visualDetails.setAlwaysFlag(z.getAlwaysFlag());
//		visualDetails.setBackgroundColor(z.getBackgroundColor());
//		visualDetails.setBorder(z.getBorder());
//		visualDetails.setCategory(z.getCategory());
//		visualDetails.setElementName(z.getElementName());
//		visualDetails.setElementType(z.getType());
//		visualDetails.setFont(z.getFont());
//		visualDetails.setFormula(z.getFormula());
//		visualDetails.setMinimalHeight(z.getMinimalHeight());
//		visualDetails.setMinimalWidth(z.getMinimalWidth());
//		visualDetails.setxPosition(z.getxPosition());
//		visualDetails.setyPosition(z.getyPosition());
//		visualDetails.setElementId(z.getElementId());
//		visualDetails.setBackgroundColorAlpha(z.getBackgroundColorAlpha());
//		visualDetails.setChartType(z.getChartType());
//		visualDetails.setChartLegend(z.getChartLegend());
//		visualDetails.setChartName(z.getChartName());
//		visualDetails.setChartPlotArea(z.getChartPlotArea());
//		visualDetails.setChartTitle(z.getChartTitle());
//		visualDetails.setChartAxes(z.getAxisListJSON());
//		visualDetails.setParentId(z.getParentId());
//		visualDetails.setMaximumWidth(z.getMaxWidth());
//		visualDetails.setMaximumHeight(z.getMaxHeight());
//		visualDetails.setHorizontalAnchorId(z.getHorizontalAnchorId());
//		visualDetails.setHorizontalAnchorType(z.getHorizontalAnchorType());
//		visualDetails.setVerticalAnchorId(z.getVerticalAnchorId());
//		visualDetails.setVerticalAnchorType(z.getVerticalAnchorType());
//		visualDetails.setLayoutList(z.getZonesListJSON());
//		visualDetails.setAlerterId(z.getAlerterId());
//		
//		return visualDetails;
//	}

//	public static AnalysisSemanticColumns analysisSematicEntityBuilder(BOItem boItem) {
//		// TODO Auto-generated method stub
//		
//		AnalysisSemanticColumns analysisSemanticColumnsObj = new AnalysisSemanticColumns();
//		
//		analysisSemanticColumnsObj.setColumnNames(boItem.getName());
//		analysisSemanticColumnsObj.setDataType(boItem.getDataType());
//		analysisSemanticColumnsObj.setFunctions(boItem.getSelect());
//		analysisSemanticColumnsObj.setObjectIdentifier(boItem.getObjectIdentifier());
//		
//		return analysisSemanticColumnsObj;
//	}

	public static StrategizerQueryConversion StrategizerQueryEntityBuilder(StrategizerQueryModel x) {
		// TODO Auto-generated method stub
		
		StrategizerQueryConversion strategizerQueryConversionObj = new StrategizerQueryConversion();
		
		strategizerQueryConversionObj.setConvertedQueryName(x.getConvertedQueryName());
		strategizerQueryConversionObj.setConvertedQueryStatement(x.getConvertedQueryStatement());
		strategizerQueryConversionObj.setDatabaseName(x.getDatabaseName());
		strategizerQueryConversionObj.setDatabaseType(x.getDatabaseType());
		strategizerQueryConversionObj.setHostname(x.getHostname());
		strategizerQueryConversionObj.setQueryStatement(x.getQueryStatement());
		strategizerQueryConversionObj.setReportId(x.getReportId());
		strategizerQueryConversionObj.setQueryName(x.getQueryName());
		strategizerQueryConversionObj.setReportName(x.getReportName());
		
		return strategizerQueryConversionObj;
	}

	public static StrategizerCalculatedColumn StrategizerCalculatedColumnBuilder(StrategizerCalculatedFormulaModel x) {
		// TODO Auto-generated method stub
		
		StrategizerCalculatedColumn strategizerCalculatedColumnObj = new StrategizerCalculatedColumn();
		
		strategizerCalculatedColumnObj.setReportId(x.getReportId());
		strategizerCalculatedColumnObj.setReportTabId(x.getReportTabId());
		strategizerCalculatedColumnObj.setFormula(x.getFormula());
		strategizerCalculatedColumnObj.setCalculatedFormula(x.getCalculatedFormula());
		strategizerCalculatedColumnObj.setColumnQualification(x.getColumnQualification());
	
		return strategizerCalculatedColumnObj;

	}



	public static ReportStrategizer reportStrategizerBuilder(ReportStrategizerModel reportTaskModel) {

		ReportStrategizer p = new ReportStrategizer();
		
		p.setAnalysisTaskId(reportTaskModel.getAnalysisTaskId());
		p.setTaskName(reportTaskModel.getTaskName());
		p.setTaskStatus(taskStatusEntityBuilder(reportTaskModel.getTaskStatus()));
		p.setSourceReportType(reportTaskModel.getSourceReportType());
		p.setTargetReportType(reportTaskModel.getTargetReportType());
		p.setProjectReportTargetCon(projectReportTargetConEntityBuilderCopy(reportTaskModel.getProjectReportTargetCon()));
		return p;
		
		
	}

	public static StrategizerVisualizationConversion strategizerVisualConvertor(StrategizerVisualizationConvertor x) {
		// TODO Auto-generated method stub
		
		StrategizerVisualizationConversion y = new StrategizerVisualizationConversion();
		
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
		y.setSourceElementId(x.getSourceElementId());
		
		
		return y;
	}

//	public static ComplexityReport ComplexityReportEntityBuilder(CognosAnalyzerModel x) {
//		// TODO Auto-generated method stub
//		
//		ComplexityReport c = new ComplexityReport();
//		
//		c.setReportId(String.valueOf(x.getReportId()));
//		c.setReportName(x.getReportName());
//		c.setComplexityParameter(x.getComplexityParameterJSON());
//		
//		return c;
//	}
//	
//	public static ComplexityReport ComplexityReportEntityBuilder(BOAnalyzerModel x) {
//		// TODO Auto-generated method stub
//		
//		ComplexityReport c = new ComplexityReport();
//		
//		c.setReportId(String.valueOf(x.getReportId()));
//		c.setReportName(x.getReportName());
//		c.setComplexityParameter(x.getBOComplexityParameterJSON());
//		
//		return c;
//	}
//	
//	
//	
//	
//	public static CommonalityParams commonalityParamsEntityBuilder(BOCommonalityParams b) {
//		CommonalityParams u = new CommonalityParams();
//		u.setReportId(b.getReportId().toString());
//		u.setReportName(b.getReportName());
//		u.setTableName(b.getTableName());
//		u.setColumnName(b.getColumnName());
//		u.setColumnAlias(b.getColumnAlias());
//		u.setColumnType(b.getColumnType());
//		u.setDataSourceName(b.getDataSourceName());
//		u.setDimensionList(b.getDimensionList());
//		u.setMeasureList(b.getMeasureList());
//		u.setVariableList(b.getVariableList());
//		u.setAttributeList(b.getAttributeList());
//		return u;
//	}
	
	public static ComplexityReport ComplexityReportEntityBuilder(TableauAnalyzerModel x) {
		ComplexityReport c = new ComplexityReport();
		
		c.setReportId(String.valueOf(x.getReportId()));
		c.setReportName(x.getReportName());
		c.setComplexityParameter(x.getTableauComplexityParametersJSON());
		
		return c;
	}
	
	public static VisualDetails visualDetailsEntityBuilder(TableauVisualElements z) {
		VisualDetails visualDetails = new VisualDetails();
		
		visualDetails.setAllignment(z.getAllignment());
		visualDetails.setAlwaysFlag(z.getAlwaysFlag());
		visualDetails.setBackgroundColor(z.getBackgroundColor());
		visualDetails.setBorder(z.getBorder());
		visualDetails.setCategory(z.getCategory());
		visualDetails.setElementName(z.getElementName());
		visualDetails.setElementType(z.getType());
		visualDetails.setFont(z.getFont());
		visualDetails.setFormula(z.getFormula());
		visualDetails.setMinimalHeight(z.getMinimalHeight());
		visualDetails.setMinimalWidth(z.getMinimalWidth());
		visualDetails.setxPosition(z.getxPosition());
		visualDetails.setyPosition(z.getyPosition());
		visualDetails.setElementId(z.getElementId());
		visualDetails.setBackgroundColorAlpha(z.getBackgroundColorAlpha());
		visualDetails.setChartType(z.getChartType());
		visualDetails.setChartLegend(z.getChartLegend());
		visualDetails.setChartName(z.getChartName());
		visualDetails.setChartPlotArea(z.getChartPlotArea());
		visualDetails.setChartTitle(z.getChartTitle());
		visualDetails.setChartAxes(z.getAxisListJSON());
		visualDetails.setParentId(z.getParentId());
		visualDetails.setMaximumWidth(z.getMaxWidth());
		visualDetails.setMaximumHeight(z.getMaxHeight());
		return visualDetails;
	}
	
	
	
}
