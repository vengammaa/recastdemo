package com.lti.recast.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.recast.jpa.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, String> {

}
