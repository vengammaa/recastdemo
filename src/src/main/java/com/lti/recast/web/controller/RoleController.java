package com.lti.recast.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.recast.service.RoleService;
import com.lti.recast.web.model.RoleModel;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "http://localhost:4200")
public class RoleController {
	private Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired(required = false)
	private RoleService roleService;
	
	@GetMapping("")
	public List<RoleModel> getRoles() {
		logger.debug("---Inside getRoles---");
		return roleService.getAllRoles();
	}

}
