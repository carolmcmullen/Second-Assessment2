package com.cooksys.secondassessment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.HashtagDto;
import com.cooksys.secondassessment.entity.Hashtag;
import com.cooksys.secondassessment.mapper.HashtagMapper;
import com.cooksys.secondassessment.repository.HashtagRepository;

@Service
public class HashtagService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	private HashtagRepository hashtagRepository;
	private HashtagMapper hashtagMapper;
		
	public HashtagService(HashtagRepository hashtagRepository, HashtagMapper hashtagMapper) {
		this.hashtagRepository = hashtagRepository;
		this.hashtagMapper = hashtagMapper;
	}

	public HashtagDto createHashtag(Hashtag hashtag) {
		Hashtag newHashtag= hashtagRepository.save(hashtag);
		return hashtagMapper.toHastTagDto(newHashtag);
	}

	public List<HashtagDto> getAll() {
		return hashtagRepository.findAll().stream().map(hashtagMapper::toHastTagDto).collect(Collectors.toList());
	}
	
	public boolean checkIfHashTagExist(String label) {
		Hashtag hashtag = hashtagRepository.findByLabel(label);
		if(hashtag == null)
			return false;
		return true;
	}
}
