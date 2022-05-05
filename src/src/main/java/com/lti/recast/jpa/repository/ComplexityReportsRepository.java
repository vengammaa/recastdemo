package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.lti.recast.jpa.entity.ComplexityReport;

public interface ComplexityReportsRepository extends JpaRepository<ComplexityReport,Integer>{

	List<ComplexityReport> findBytaskId(Integer taskId);
	
}
