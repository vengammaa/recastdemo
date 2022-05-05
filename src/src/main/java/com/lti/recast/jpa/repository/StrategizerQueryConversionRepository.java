package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.recast.jpa.entity.StrategizerQueryConversion;



public interface StrategizerQueryConversionRepository extends JpaRepository<StrategizerQueryConversion, Integer>{


	List<StrategizerQueryConversion> findByStratTaskId(int stratTaskId);

	
	
}
