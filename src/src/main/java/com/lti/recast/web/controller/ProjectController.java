package com.lti.recast.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.recast.service.ProjectService;
import com.lti.recast.web.model.ProjectModel;
import com.lti.recast.web.model.UserModel;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/projects")
public class ProjectController {
	private Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired(required = false)
	private ProjectService projectService;
	
	@PostMapping("/createProject")
	public ProjectModel createProject(@RequestBody ProjectModel newProject) {
		logger.info("---Inside create project---");
		return projectService.save(newProject);
	}
	
	@GetMapping("")
	public List<ProjectModel> getAllProjects() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getAuthorities().toString().contains("ROLE_ADMIN")) return projectService.getAllProjects();
		else return projectService.getProjectbyUser(auth.getName());
	}
	
	@GetMapping("/{id}")
	public ProjectModel getProject(@PathVariable int id) {
		logger.info("---Inside getProject---");
		return projectService.getProjectById(id);
	}
	
	@DeleteMapping("/{id}")
	public int deleteProject(@PathVariable int id) {
		logger.info("---Inside deleteProject---");
		projectService.deleteProject(id);
		return id;
	}
	
	@PutMapping("/addUsers/{projectId}")
	public ProjectModel addUserToProject(@RequestBody List<UserModel> users, @PathVariable int projectId) {
		logger.info("---Inside addUserToProject---");
		return projectService.addUsertoProject(users, projectId);
	}
	
	@GetMapping("/userProjects")
	public List<ProjectModel> getProjectsByUser(@RequestBody UserModel user) {
		logger.info("---Inside getProjcetsByUser---");
		return projectService.getProjectbyUser(user.getUserName());
	}
	
	@GetMapping("otherUsers/{projectId}")
	public List<UserModel> getOtherUsers(@PathVariable int projectId) {
		logger.info("---Inside getOtherUsers---");
		return projectService.getOtherUsers(projectId);
	}
	
	@PostMapping("removeUsers/{projectId}")
	public String removeUsers(@PathVariable int projectId, @RequestBody List<UserModel> users) {
		return projectService.removeUsers(projectId, users);
	}
	
	
}
