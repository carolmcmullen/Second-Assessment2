package com.cooksys.secondassessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cooksys.secondassessment.entity.Mention;

public interface MentionRepository extends JpaRepository<Mention, Integer> {
	
	/*
	 * Checks whether or not a given mention exists.
	 */
	@Query( "SELECT m FROM Mention m where m.username = ?")
	List<Mention> findByUsername(String username);

}
