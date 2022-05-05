package com.lti.recast.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.recast.jpa.entity.Project;
import com.lti.recast.jpa.entity.User;
import com.lti.recast.jpa.repository.ProjectRepository;
import com.lti.recast.jpa.repository.UserRepository;
import com.lti.recast.util.EntityBuilder;
import com.lti.recast.util.ModelBuilder;
import com.lti.recast.web.model.ProjectModel;
import com.lti.recast.web.model.UserModel;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private StatusService statusService;
	
	private Logger logger = LoggerFactory.getLogger(ProjectService.class);
	
	@Transactional
	public ProjectModel getProjectById(Integer projectId) {
		logger.debug("Inside ProjectService -> getProjectById()");
		return ModelBuilder.projectModelBuilder(projectRepository.getOne(projectId));
	}
	
	@Transactional
	public List<ProjectModel> getAllProjects() {
		logger.debug("Inside ProjectService -> getAllProjects()");
		return projectRepository.findAll().stream().map(ModelBuilder::projectModelBuilder).collect(Collectors.toList());
	}
	
	@Transactional
	public List<ProjectModel> getProjectbyUser(String userName) {
		logger.debug("Inside ProjectService -> getProjectByUser");
		return projectRepository.findByUser(userName).stream().map(ModelBuilder::projectModelBuilder).collect(Collectors.toList());
		
	}
	
	@Transactional
	public ProjectModel addUsertoProject(List<UserModel> users, int projectId) {
		Project p = projectRepository.getOne(projectId);
		for(UserModel user : users) {
			User u = userRepository.getOne(user.getUserName());
			p.addUser(u);
		}
		p = projectRepository.save(p);
		return ModelBuilder.projectModelBuilder(p);
	}
	
	@Transactional
	public List<UserModel> getOtherUsers(int projectId) {
		Project p = projectRepository.getOne(projectId);
		List<String> ids = p.getUsers().stream().map(x -> x.getUserName()).collect(Collectors.toList());
		return userRepository.findByUserNameNotIn(ids).stream().map(ModelBuilder::userModelBuilder).collect(Collectors.toList());
	}
	
	@Transactional
	public String removeUsers(int projectId, List<UserModel> users) {
		String usernames = String.join(", ", users.stream().map(x -> x.getUserName()).collect(Collectors.toList()));
		users.forEach(x -> projectRepository.removeUser(projectId, x.getUserName()));
		return "Removed " + usernames + " from project " + projectId + ".";
	}
	
	
	@Transactional
	public ProjectModel save(ProjectModel projectModel) {
		logger.debug("Inside ProjectService -> save()");
		
		projectModel.setUsers(projectModel.getUsers().stream().map(x -> userService.getUserByUserName(x.getUserName())).collect(Collectors.toList()));
		projectModel.setStatus(statusService.getStatusByCode(projectModel.getStatus().getCode()));
		logger.debug(projectModel.toString());
		Project project = EntityBuilder.projectEntityBuilder(projectModel);
		project = projectRepository.save(project);
		return ModelBuilder.projectModelBuilder(project);
	}
	
	@Transactional
	public void deleteProject(int projectId) {
		logger.info("Deleting projectId: " + projectId);
		projectRepository.deleteById(projectId);
	}
}
