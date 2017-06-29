package com.cooksys.secondassessment.mapper;

import org.mapstruct.Mapper;

import com.cooksys.secondassessment.dto.TweetDto;
import com.cooksys.secondassessment.entity.Tweet;

@Mapper(componentModel = "spring")
public interface TweetMapper {	
	TweetDto toTweetDto(Tweet tweet);
	Tweet toTweet(TweetDto tweet);
}
