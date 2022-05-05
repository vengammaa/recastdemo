package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.recast.jpa.entity.StrategizerCalculatedColumn;

public interface StrategizerCalculatedColumnRepository extends JpaRepository<StrategizerCalculatedColumn,Integer>{

	List<StrategizerCalculatedColumn> findByStratTaskId(int startTaskId);

}
