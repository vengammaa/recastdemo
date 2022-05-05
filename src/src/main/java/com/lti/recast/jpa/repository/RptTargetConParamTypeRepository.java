package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.recast.jpa.entity.RptConParamType;
import com.lti.recast.jpa.entity.RptTargetConParamType;

public interface RptTargetConParamTypeRepository extends JpaRepository<RptTargetConParamType, String> {
	List<RptTargetConParamType> findByReportTypeCode(String code);
}
