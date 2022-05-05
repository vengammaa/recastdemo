package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.recast.jpa.entity.StrategizerCalculations;

public interface StrategizerCalculationsRepository extends JpaRepository<StrategizerCalculations,Integer>{

	List<StrategizerCalculations> findByStratTaskId(int startTaskId);
}
