package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lti.recast.jpa.entity.ProjectReportCon;
import com.lti.recast.jpa.entity.ProjectReportTargetCon;

public interface ProjectReportTargetConRepository extends JpaRepository<ProjectReportTargetCon, Integer> {
	@Query(value = "SELECT * FROM project_report_target_con WHERE report_type_code = ?1", nativeQuery = true)
	List<ProjectReportTargetCon> findByReportTypeCode(String reportTypeCode);
}
