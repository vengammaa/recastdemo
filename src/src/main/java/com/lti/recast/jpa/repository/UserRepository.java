package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.recast.jpa.entity.User;


public interface UserRepository extends JpaRepository<User, String> {

	List<User> findByUserNameNotIn(List<String> usernames);

}
