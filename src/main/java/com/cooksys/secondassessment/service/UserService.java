package com.cooksys.secondassessment.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.UserWithIdDto;
import com.cooksys.secondassessment.dto.UserWithoutIdDto;
import com.cooksys.secondassessment.entity.Profile;
import com.cooksys.secondassessment.entity.User;
import com.cooksys.secondassessment.mapper.UserMapper;
import com.cooksys.secondassessment.repository.JpaUserRepository;

import javassist.NotFoundException;

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
		user.setJoined(new Timestamp(System.currentTimeMillis()));
		user.setIsActive(true);
		User newUser = jpaUserRepository.save(user);
		return userMapper.toUserWithIdDto(newUser);
	}

	public List<UserWithIdDto> getAll() {
		return jpaUserRepository.findAll().stream().map(userMapper::toUserWithIdDto).collect(Collectors.toList());
	}
	
	public UserWithoutIdDto deleteUser(User user) throws NotFoundException {
		User dbUser = checkCredentials(user);
		if(dbUser != null) {
			dbUser.setIsActive(false);
			User newUser = jpaUserRepository.save(dbUser);
			return userMapper.toUserWithoutIdDto(newUser);
		}
		else {
			throw new NotFoundException("user credential invalid");
		}
	}
	
	public boolean isUsernameAvailable(String username){
		User dbUser = jpaUserRepository.findByUsername(username);
		if(dbUser == null)
			return true;
		return false;
	}
	
	/*
	 * Check if user credential is valid
	 */
	private User checkCredentials(User user) {
		User dbUser = jpaUserRepository.findByUsername(user.getUsername());
		if(dbUser == null)
			return null;
		if(dbUser.getPassword().equals(user.getPassword()))
			return dbUser;
		return null;
	}

	public UserWithoutIdDto updateProfile(User user, Profile profile) throws NotFoundException {
		User dbUser = checkCredentials(user);
		if(dbUser != null && dbUser.getIsActive()) {
			dbUser.setProfile(profile);
			User newUser = jpaUserRepository.save(dbUser);
			return userMapper.toUserWithoutIdDto(newUser);
		}
		throw new NotFoundException("user is invalid");
	}

	public UserWithoutIdDto getUser(String username) throws NotFoundException {
		User user = jpaUserRepository.findByUsername(username);
		if(user != null)
		{
			return userMapper.toUserWithoutIdDto(user);
		}
		throw new NotFoundException("user is invalid"); 
	}
}
