package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lti.recast.jpa.entity.UniverseReport;

public interface UniverseReportRepository extends JpaRepository<UniverseReport, Integer> {
	List<UniverseReport> findByPrjRptAnalysisId(int prjRptAnalysisId);

	@Query(value = "select * from universe_report where project_id=?1 and project_report_con_id=?2", nativeQuery = true)
	List<UniverseReport> getUniverseNames(int id);
	
	@Query(value = "select name, tables, connection_class, db_name from recast.universe_report as unv where unv.prj_rpt_analysis_id = ?1 and unv.name != 'Parameters'", nativeQuery = true)
	List<List<String>> getDatasourceDetails(int taskId);
	
	@Query(value = "select name, tables, connection_class from recast.universe_report as unv where unv.prj_rpt_analysis_id = ?1", nativeQuery = true)
	List<UniverseReport> getDatasourceDetailsTDS(int taskId);
	
}
