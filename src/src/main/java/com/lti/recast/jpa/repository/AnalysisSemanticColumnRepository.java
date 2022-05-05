package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.recast.jpa.entity.AnalysisSemanticColumns;

public interface AnalysisSemanticColumnRepository extends JpaRepository<AnalysisSemanticColumns, Integer> {

	List<AnalysisSemanticColumns> findByTaskId (int taskId);
}
