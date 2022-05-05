package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.recast.jpa.entity.StrategizerMetadataColumns;


public interface StrategizerMetadataColumnRepository extends JpaRepository<StrategizerMetadataColumns,Integer>{

	List<StrategizerMetadataColumns> findByStratTaskId(int stratTaskId);
}
