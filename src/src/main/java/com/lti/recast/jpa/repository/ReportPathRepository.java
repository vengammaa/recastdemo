package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.lti.recast.jpa.entity.ReportPath;


@Repository
public interface ReportPathRepository extends JpaRepository<ReportPath,Integer> {
	List<ReportPath> findByPathId(Integer pathId);
	

}
