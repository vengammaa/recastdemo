package com.lti.recast.jpa.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.recast.jpa.entity.ReportStrategizer;

public interface ReportStrategizerRepository extends JpaRepository<ReportStrategizer, Integer>{

	List<ReportStrategizer> findAllByProjectReportTargetConId(int projectReportTargetConId);

	
}
