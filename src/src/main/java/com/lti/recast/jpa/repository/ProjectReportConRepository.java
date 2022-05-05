package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lti.recast.jpa.entity.ProjectReportCon;

@Repository
public interface ProjectReportConRepository extends JpaRepository<ProjectReportCon, Integer> {
	@Query(value = "SELECT * FROM project_report_con WHERE project_id = ?1 AND report_type_code = ?2", nativeQuery = true)
	 List<ProjectReportCon> findByIdAndReporttype(int id,String reporttype);	
}
