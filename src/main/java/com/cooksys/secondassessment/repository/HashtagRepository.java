package com.cooksys.secondassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cooksys.secondassessment.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {
	
	/*
	 * Checks whether or not a given username exists.
	 */
	@Query( "SELECT h FROM Hashtag h where h.label = ?")
	Hashtag findByLabel(String label);

}
