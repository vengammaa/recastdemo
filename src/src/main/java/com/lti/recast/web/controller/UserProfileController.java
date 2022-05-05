package com.lti.recast.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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


import com.lti.recast.service.UserService;
import com.lti.recast.web.model.UserModel;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
public class UserProfileController{
	private Logger logger = LoggerFactory.getLogger(UserProfileController.class);
	@Autowired(required = false)
	private UserService userService;
	
	@PostMapping("/createUser")
	public UserModel createUser(@RequestBody UserModel newUser) {
		logger.info("---Inside create user---");
		return userService.save(newUser);
	}
	
	@GetMapping("")
	public List<UserModel> allUsers() throws AccessDeniedException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.getAuthorities().toString().contains("ROLE_ADMIN")) throw new AccessDeniedException("You do not have the permission to access this resource");
		return userService.getAllUsers();
	}
	
	@GetMapping("/{username}") 
	public UserModel getUser(@PathVariable String username) {
		return userService.getUserByUserName(username);
	}
	
	@PutMapping("/{username}")
	public UserModel editUserProfile(@RequestBody UserModel user, @PathVariable String username) {
		return userService.editUserProfile(user, username);
	}
	
	@DeleteMapping("/{username}")
	public String deleteUser(@PathVariable String username) {
		userService.deleteUser(username);
		return username;
	}
}
