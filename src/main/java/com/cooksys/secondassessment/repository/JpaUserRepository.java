package com.cooksys.secondassessment.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.entity.User;

public interface JpaUserRepository extends JpaRepository<User, Integer> {
	
	//@Transactional
	//public User create(User user) {
	//	return user;
	}

	//List<User> getAll(user);
	//}
	
	//List<User> findByCredentials_Username(String username);
	//List<User> findByCredentials_UsernameAndCredentials_Password(Credentials_Password(String username, String password));
	

