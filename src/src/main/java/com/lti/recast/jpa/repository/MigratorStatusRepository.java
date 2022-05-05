package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.recast.jpa.entity.MigratorStatus;

public interface MigratorStatusRepository extends JpaRepository<MigratorStatus, Integer> {
	
	List<MigratorStatus> findByStratTaskId(int startTaskId);

}
