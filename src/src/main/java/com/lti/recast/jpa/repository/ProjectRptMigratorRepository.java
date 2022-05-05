package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.lti.recast.jpa.entity.PrjRptMigrator;

public interface ProjectRptMigratorRepository extends JpaRepository<PrjRptMigrator, Integer> {

	List<PrjRptMigrator> findAllByProjectId(int id);
}
