package com.lti.recast.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lti.recast.jpa.entity.Role;
import com.lti.recast.jpa.entity.User;
import com.lti.recast.jpa.repository.UserRepository;
import com.lti.recast.util.EntityBuilder;
import com.lti.recast.util.ModelBuilder;
import com.lti.recast.web.model.RoleModel;
import com.lti.recast.web.model.UserModel;
import com.lti.recast.web.model.UserProfileModel;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	
	@Transactional
	public UserModel getUserByUserName(String userName) {
		logger.debug("Inside UserService-->getUserByUserName ...");
		User user = userRepository.getOne(userName);
		UserModel userModel = ModelBuilder.userModelBuilder(user);
		return userModel;
	}

	@Transactional
	public List<UserModel> getAllUsers() {
		logger.debug("Inside UserService-->getAllUsers ...");
		List<User> users = userRepository.findAll();
		List<UserModel> userModels = new ArrayList<UserModel>();
		for (User user : users) {
			UserModel userModel = ModelBuilder.userModelBuilder(user);
			userModels.add(userModel);
		}
		return userModels;
	}

	@Transactional
	public List<RoleModel> getAvailableRolesByUserName(String userName) {
		logger.debug("Inside UserService-->getAvailableRolesByUserName ...");
		User user = userRepository.getOne(userName);
		Set<Role> roles = user.getRoles();
		List<RoleModel> roleModels = new ArrayList<RoleModel>();
		for (Role role : roles) {
			RoleModel roleModel = ModelBuilder.roleModelBuilder(role);
			roleModels.add(roleModel);
		}
		return roleModels;
	}
	
	@Transactional
	public UserModel editUserProfile(UserModel userModel, String username) {
		logger.debug("Inside UserService -> edit");
		User user = userRepository.getOne(username);
		UserProfileModel userProfileModel = userModel.getUserProfile();	
		user.getUserProfile().setEmailid(userProfileModel.getEmailid());
		user.getUserProfile().setMobileNo(userProfileModel.getMobileNo());
		user.getUserProfile().setName(userProfileModel.getName());
		user.setRoles(userModel.getRoles().stream().map(EntityBuilder::roleEntityBuilder).collect(Collectors.toSet()));
		userRepository.save(user);
		return ModelBuilder.userModelBuilder(user);
	}
	
	@Transactional
	public void deleteUser(String userName) {
		logger.debug("Inside UserService -> delete");
		userRepository.deleteById(userName);
	}

	@Transactional
	public UserModel save(UserModel userModel) {
		logger.debug("Inside UserService-->save ...");
		User user = EntityBuilder.userEntityBuilder(userModel);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user = userRepository.save(user);
		return ModelBuilder.userModelBuilder(user);
	}
	
	public static void main(String args[]) {
		BCryptPasswordEncoder e = new BCryptPasswordEncoder();
		System.out.println(e.encode("admin@123456"));
	}

}
