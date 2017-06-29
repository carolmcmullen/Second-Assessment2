package com.cooksys.secondassessment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.TweetDto;
import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.mapper.TweetMapper;
import com.cooksys.secondassessment.repository.TweetRepository;

@Service
public class TweetService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;
		
	public TweetService(TweetRepository tweetRepository, TweetMapper tweetMapper) {
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
	}

	public TweetDto createTweet(Tweet tweet) {
		Tweet newTweet= tweetRepository.save(tweet);
		return tweetMapper.toTweetDto(newTweet);
	}

	public List<TweetDto> getAll() {
		return tweetRepository.findAll().stream().map(tweetMapper::toTweetDto).collect(Collectors.toList());
	}
}
