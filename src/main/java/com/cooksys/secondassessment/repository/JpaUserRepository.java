package com.cooksys.secondassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cooksys.secondassessment.entity.User;

public interface JpaUserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT u FROM User u where u.username = ?")
	User findByUsername(String username);

}
