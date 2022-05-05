package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.recast.jpa.entity.CommonalityReport;

public interface CommonalityReportRepository extends JpaRepository<CommonalityReport, Integer> {
	List<CommonalityReport> findByPrjRptAnalysisId(int prjRptAnalysisId);
	List<CommonalityReport> deleteByPrjRptAnalysisId(int prjRptAnalysisId);
}
