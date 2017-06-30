package com.cooksys.secondassessment.service;

import java.sql.Timestamp;
import java.util.ArrayList;
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

	public UserWithIdDto createUser(User user) throws Exception {	
		if(this.isUsernameAvailable(user.getUsername()))
		{
			user.setJoined(new Timestamp(System.currentTimeMillis()));
			user.setIsActive(true);
			User newUser = jpaUserRepository.save(user);
			return userMapper.toUserWithIdDto(newUser);
		}
		else
		{
			User dbUser = this.checkCredentials(user);
			if(dbUser != null)
			{
				dbUser.setIsActive(true);
				User newUser = jpaUserRepository.save(dbUser);
				return userMapper.toUserWithIdDto(newUser);
			}
		}
		throw new Exception("Failed to create user");
		
	}

	public List<UserWithIdDto> getAll() {
		return jpaUserRepository.findAll().stream().map(userMapper::toUserWithIdDto).collect(Collectors.toList());
	}
	
	public UserWithoutIdDto deleteUser(User user) throws Exception {
		User dbUser = checkIfUserIsValid(user);
			dbUser.setIsActive(false);
			User newUser = jpaUserRepository.save(dbUser);
			return userMapper.toUserWithoutIdDto(newUser);
		
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
	public User checkCredentials(User user) {
		User dbUser = jpaUserRepository.findByUsername(user.getUsername());
		if(dbUser == null)
			return null;
		if(dbUser.getPassword().equals(user.getPassword()))
			return dbUser;
		return null;
	}
	
	/*
	 * Check if user credential is valid
	 */
	public User checkIfUserIsValid(User user) throws Exception {
		User dbUser = jpaUserRepository.findByUsername(user.getUsername());
		if(dbUser == null)
			throw new Exception("Invalid user");
		if(dbUser.getPassword().equals(user.getPassword()) && dbUser.getIsActive())
			return dbUser;
		throw new Exception("Invalid user");
	}

	public UserWithoutIdDto updateProfile(User user, Profile profile) throws Exception {
		User dbUser = checkIfUserIsValid(user);
			dbUser.setProfile(profile);
			User newUser = jpaUserRepository.save(dbUser);
			return userMapper.toUserWithoutIdDto(newUser);
	}

	public UserWithoutIdDto getUser(String username) throws NotFoundException {
		User user = jpaUserRepository.findByUsername(username);
		if(user != null)
		{
			return userMapper.toUserWithoutIdDto(user);
		}
		throw new NotFoundException("user is invalid"); 
	}
	
	/*
	 * Subscribes the user whose credentials are provided by the request body to the user whose username is given in the url. 
	 * If there is already a following relationship between the two users, no such followable user exists (deleted or never created), 
	 * or the credentials provided do not match an active user in the database, an error should be sent as a response. 
	 * If successful, no data is sent.
	 */
	public void followUser(String username, User user) throws Exception {
		User follower = checkCredentials(user);		
		
		if(follower == null)
			throw new Exception("User is invalid");
		
		// check if already following
		for(int i = 0; i < follower.getFollowing().size(); ++i){
			if(follower.getFollowing().get(i).getUsername().equals(username) &&
					follower.getFollowing().get(i).getIsActive())	
				throw new Exception("Already following user");
		}		
		
		User followee = jpaUserRepository.findByUsername(username);
		if(followee == null)
			throw new Exception("User is invalid");
		
		List<User> userList = new ArrayList<User>();
		userList.add(followee);
		follower.setFollowing(userList);
		jpaUserRepository.save(follower);
		
		List<User> userList2 = new ArrayList<User>();
		userList2.add(follower);
		followee.setFollowers(userList2);
		jpaUserRepository.save(followee);
	}

	/*
	 * Unsubscribes the user whose credentials are provided by the request body from the user whose username is given in the url. 
	 * If there is no preexisting following relationship between the two users, no such followable user exists (deleted or never created), 
	 * or the credentials provided do not match an active user in the database, an error should be sent as a response. If successful, no data is sent.
	 */
	public boolean unFollowUser(String username, User user) throws Exception {
		User follower = checkCredentials(user);		
		
		if(follower == null)
			throw new Exception("User is invalid");
		
		// check if following
		for(int i = 0; i < follower.getFollowing().size(); ++i){
			if(follower.getFollowing().get(i).getUsername().equals(username) &&
					follower.getFollowing().get(i).getIsActive())
			{
				follower.getFollowing().remove(i);
				jpaUserRepository.save(follower);
				return true;
			}				
		}	
		// not following user return false
		return false;		
		
	}

	public List<UserWithIdDto> getFollowers(String username) throws Exception {
		User user = jpaUserRepository.findByUsername(username);
		if(user != null)
		{
			return user.getFollowers()
					.stream()
					.filter(u -> u.getIsActive().equals(true))
					.map(userMapper::toUserWithIdDto)
					.collect(Collectors.toList());
		}
		throw new Exception("Cannot find user");
	}

	public List<UserWithIdDto> getFollowing(String username) throws Exception {
		User user = jpaUserRepository.findByUsername(username);
		if(user != null)
		{
			return user.getFollowing()
					.stream()
					.filter(u -> u.getIsActive().equals(true))
					.map(userMapper::toUserWithIdDto)
					.collect(Collectors.toList());
		}
		throw new Exception("Cannot find user");
	}
}
