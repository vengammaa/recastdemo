package com.lti.recast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.recast.jpa.repository.StatusRepository;
import com.lti.recast.util.ModelBuilder;
import com.lti.recast.web.model.StatusModel;

@Service
public class StatusService {
	
	@Autowired
	private StatusRepository repository;
	
	@Transactional
	public StatusModel getStatusByCode(String statusCode) {
		return ModelBuilder.statusModelBuilder(repository.getOne(statusCode));
	}
	
}
