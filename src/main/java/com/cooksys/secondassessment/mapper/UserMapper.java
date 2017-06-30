package com.cooksys.secondassessment.mapper;

import org.mapstruct.Mapper;

import com.cooksys.secondassessment.dto.CredentialDto;
import com.cooksys.secondassessment.dto.UserWithIdDto;
import com.cooksys.secondassessment.dto.UserWithoutIdDto;
import com.cooksys.secondassessment.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserWithIdDto toUserWithIdDto(User user);

	User toUser(UserWithIdDto user);

	UserWithoutIdDto toUserWithoutIdDto(User user);

	User toUser(UserWithoutIdDto user);

	CredentialDto UserCredential(User user);

	User toUser(CredentialDto user);
}
