package com.cooksys.secondassessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cooksys.secondassessment.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {
	
	/*
	 * Checks whether or not a given hashtag exists.
	 */
	@Query( "SELECT h FROM Hashtag h where h.label = ?")
	List<Hashtag> findByLabel(String label);

}
