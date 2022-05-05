package com.lti.recast.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lti.recast.jpa.repository.RoleRepository;
import com.lti.recast.util.ModelBuilder;
import com.lti.recast.web.model.RoleModel;

@Service
public class RoleService {
	private Logger logger = LoggerFactory.getLogger(RoleService.class);
	
	@Autowired
	private RoleRepository roleRepository;
	
	public List<RoleModel> getAllRoles() {
		logger.debug("Inside RoleService -> getAllRoles");
		return roleRepository.findAll().stream().map(ModelBuilder::roleModelBuilder).collect(Collectors.toList());
	}
	
}
