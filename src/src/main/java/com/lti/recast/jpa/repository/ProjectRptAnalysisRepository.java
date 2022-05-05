package com.lti.recast.jpa.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lti.recast.jpa.entity.PrjRptAnalysis;
import com.lti.recast.web.model.PrjRptSourceTaskModel;

public interface ProjectRptAnalysisRepository extends JpaRepository<PrjRptAnalysis, Integer>{
	List<PrjRptAnalysis> findAllByProjectId(int id);

	@Query(value = "select * from prj_rpt_analysis where project_id=?1 and project_report_con_id=?2", nativeQuery = true)
	Set<PrjRptAnalysis> findTaskByConnId(int projectId, int connectionId);
}
