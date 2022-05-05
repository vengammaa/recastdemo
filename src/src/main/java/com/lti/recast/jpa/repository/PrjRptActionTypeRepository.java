package com.lti.recast.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.recast.jpa.entity.PrjRptActionType;

@Repository
public interface PrjRptActionTypeRepository extends JpaRepository<PrjRptActionType, String> {

}
