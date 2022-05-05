package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.recast.jpa.entity.PrjRptTargetConParams;

public interface PrjRptTargetConParamsRepository extends JpaRepository<PrjRptTargetConParams, Integer> {
	List<PrjRptTargetConParams> findByProjectReportTargetConId(int projectReportTargetConId);
}
