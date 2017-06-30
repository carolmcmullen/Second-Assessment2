package com.cooksys.secondassessment.mapper;

import org.mapstruct.Mapper;

import com.cooksys.secondassessment.dto.HashtagDto;
import com.cooksys.secondassessment.entity.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
	HashtagDto toHashtagDto(Hashtag hashtag);

	Hashtag toHashtag(HashtagDto hashtag);
}
