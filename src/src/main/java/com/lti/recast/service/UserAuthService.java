package com.lti.recast.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lti.recast.web.model.RoleModel;
import com.lti.recast.web.model.UserModel;

@Service
public class UserAuthService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(UserAuthService.class);

	@Autowired
	private UserService userService;

	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		logger.debug("Inside UserAuthService --> loadUserByUsername ...");

		UserModel appUser = userService.getUserByUserName(username);

		if (appUser == null) {
			throw new UsernameNotFoundException("User: '" + username + "' not found.");
		}

		List<RoleModel> userRoles = appUser.getRoles();
		List<GrantedAuthority> grantedAutorities = new ArrayList<GrantedAuthority>();
		for (RoleModel r : userRoles) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(r.getRoleName());
			grantedAutorities.add(grantedAuthority);
		}

		UserDetails userDetails = new User(appUser.getUserName(), appUser.getPassword(), appUser.isAccountEnabled(),
				true, true, !appUser.isAccountLocked(), grantedAutorities);
		return userDetails;

	}

}