package com.cooksys.secondassessment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.UserWithIdDto;
import com.cooksys.secondassessment.dto.UserWithoutIdDto;
import com.cooksys.secondassessment.entity.User;
import com.cooksys.secondassessment.mapper.UserMapper;
import com.cooksys.secondassessment.repository.JpaUserRepository;

@Service
public class UserService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	private JpaUserRepository jpaUserRepository;
	private UserMapper userMapper;
	
	JpaRepository<User, Integer> mapUserList;
	
	public UserService(JpaUserRepository jpaUserRepository, UserMapper userMapper) {
		this.jpaUserRepository = jpaUserRepository;
		this.userMapper = userMapper;
	}

	public UserWithIdDto createUser(User user) {
		User newUser = jpaUserRepository.save(user);
		return userMapper.toUserWithIdDto(newUser);
	}

	public List<UserWithIdDto> getAll() {
		return jpaUserRepository.findAll().stream().map(userMapper::toUserWithIdDto).collect(Collectors.toList());
	}
}
