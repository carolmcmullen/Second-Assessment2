package com.cooksys.secondassessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cooksys.secondassessment.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {

	@Query("SELECT t FROM Tweet t where t.user.username = ?")
	List<Tweet> findByUsername(String username);
}
