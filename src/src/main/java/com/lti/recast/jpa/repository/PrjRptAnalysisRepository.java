package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lti.recast.jpa.entity.PrjRptAnalysis;

public interface PrjRptAnalysisRepository extends JpaRepository<PrjRptAnalysis, String>{
	@Query(value = "SELECT * FROM prj_rpt_analysis WHERE project_id = ?1", nativeQuery = true)
	List<PrjRptAnalysis> findAllByProjectId(int id);
	
	@Modifying
	@Query(value = "DELETE FROM prj_rpt_analysis WHERE id = ?1", nativeQuery = true)
	void removeTaskById(int id);

}
