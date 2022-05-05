package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.recast.jpa.entity.AnalysisReportsTable;

public interface AnalysisReportTableRepository extends JpaRepository<AnalysisReportsTable, Integer>{
	List<AnalysisReportsTable> deleteByTaskId(int taskId);
	
	List<AnalysisReportsTable> findByTaskId (int taskId);
}
