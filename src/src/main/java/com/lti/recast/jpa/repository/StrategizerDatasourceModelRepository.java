package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.recast.jpa.entity.StrategizerDatasourceModel;


public interface StrategizerDatasourceModelRepository extends JpaRepository<StrategizerDatasourceModel,Integer>{
	
	List<StrategizerDatasourceModel> findByStratTaskId(int stratTaskId);

}
