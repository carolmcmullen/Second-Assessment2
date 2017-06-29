package com.cooksys.secondassessment.mapper;

import org.mapstruct.Mapper;

import com.cooksys.secondassessment.dto.ProfileDto;
import com.cooksys.secondassessment.entity.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {	
	ProfileDto toProfileDto(Profile profile);
	Profile toProfile(ProfileDto profile);
}
