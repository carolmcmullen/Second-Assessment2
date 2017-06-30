package cooksys.secondassessment.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.dto.CredentialDto;
import com.cooksys.secondassessment.dto.ProfileCredentialDto;
import com.cooksys.secondassessment.dto.TweetDto;
import com.cooksys.secondassessment.dto.UserWithIdDto;
import com.cooksys.secondassessment.dto.UserWithoutIdDto;
import com.cooksys.secondassessment.entity.User;
import com.cooksys.secondassessment.mapper.ProfileMapper;
import com.cooksys.secondassessment.mapper.TweetMapper;
import com.cooksys.secondassessment.mapper.UserMapper;
import com.cooksys.secondassessment.service.TweetService;
import com.cooksys.secondassessment.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@RestController
@RequestMapping("users")
public class UserController {

	private UserService userService;
	private UserMapper userMapper;
	private ProfileMapper profileMapper;
	private TweetService tweetService;
	private TweetMapper tweetMapper;

	public UserController(UserService userService, UserMapper userMapper, ProfileMapper profileMapper, TweetService tweetService, TweetMapper tweetMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
		this.profileMapper = profileMapper;
		this.tweetService = tweetService;
		this.tweetMapper = tweetMapper;
	}

	// Creates User

	@ApiOperation(value = "/CreateUser", nickname = "createNewUser")
	@PostMapping()
	public UserWithIdDto create(@RequestBody CredentialDto user, HttpServletResponse response) throws Exception {
		// If any required fields are missing or the username provided is
		// already taken, an error should be sent in lieu of a response.
		// If the given credentials match a previously-deleted user, re-activate
		// the deleted user instead of creating a new one.
		response.setStatus(HttpServletResponse.SC_CREATED);
		try {
			return userService.createUser(userMapper.toUser(user));
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	// Get all Users
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	// @ApiOperation(value = "get all users", nickname = "getAllUsers")
	@GetMapping()
	public List<UserWithIdDto> getAll() {
		return userService.getAll();
	}

	// Get user
	@GetMapping("@{username}")
	public UserWithoutIdDto getUser(@PathVariable String username, HttpServletResponse response)
			throws NotFoundException {
		try {
			return userService.getUser(username);
		} catch (NotFoundException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw e;
		}
	}

	// Delete user
	@DeleteMapping("@{username}")
	public UserWithoutIdDto delete(@RequestBody CredentialDto user, HttpServletResponse response) throws Exception {
		try {
			return userService.deleteUser(userMapper.toUser(user));
		} catch (NotFoundException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw e;
		}
	}

	@PatchMapping("@{username}")
	public UserWithoutIdDto updateUser(@RequestBody ProfileCredentialDto profileCredential,
			HttpServletResponse response) throws Exception {
		try {
			return userService.updateProfile(userMapper.toUser(profileCredential.getCredential()),
					profileMapper.toProfile(profileCredential.getProfile()));
		} catch (NotFoundException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw e;
		}
	}

	@PostMapping("@{username}/follow")
	public void followUser(@PathVariable String username, @RequestBody CredentialDto credential,
			HttpServletResponse response) throws Exception {
		try {
			userService.followUser(username, userMapper.toUser(credential));
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	@PostMapping("@{username}/unfollow")
	public void unFollowUser(@PathVariable String username, @RequestBody CredentialDto credential,
			HttpServletResponse response) throws Exception {
		try {
			userService.unFollowUser(username, userMapper.toUser(credential));
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw e;
		}
	}

	@GetMapping("@{username}/followers")
	public List<UserWithIdDto> getFollowers(@PathVariable String username, HttpServletResponse response)
			throws Exception {
		List<UserWithIdDto> followers;
		try {
			followers = userService.getFollowers(username);
			if (followers.size() == 0)
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return followers;
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw e;
		}

	}

	@GetMapping("@{username}/following")
	public List<UserWithIdDto> getFollowing(@PathVariable String username, HttpServletResponse response)
			throws Exception {
		List<UserWithIdDto> following;
		try {
			following = userService.getFollowing(username);
			if (following.size() == 0)
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return following;
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw e;
		}

	}
		
	  
	 @GetMapping("@{username}/tweets")
		public List<TweetDto> getUserTweets(@PathVariable String username, HttpServletResponse response) throws Exception {
		 return tweetService.getTweetsByUsername(username)
				 .stream()
				 .filter(t -> t.getIsActive().equals(true))
				 .sorted((t1, t2) -> t2.getPosted().compareTo(t1.getPosted()))
				 .map(tweetMapper::toTweetDto)
				 .collect(Collectors.toList());	 
		}
	 

	@GetMapping("@{username}/mentions")
	public List<TweetDto> getMentions(@PathVariable String username, HttpServletResponse response) throws Exception {
	 return tweetService.getTweetsByMentions(username)
			 .stream()
			 .filter(t -> t.getIsActive().equals(true))
			 .sorted((t1, t2) -> t2.getPosted().compareTo(t1.getPosted()))
			 .map(tweetMapper::toTweetDto)
			 .collect(Collectors.toList());	 
	}
	
	/*
	@GetMapping("@{username}/feed")
	public List<TweetDto> getFeed(@PathVariable String username, HttpServletResponse response) throws Exception {
	 return tweetService.getFeed(username)
			 .stream()
			 .filter(t -> t.getIsActive().equals(true))
			 .sorted((t1, t2) -> t2.getPosted().compareTo(t1.getPosted()))
			 .map(tweetMapper::toTweetDto)
			 .collect(Collectors.toList());	 
	}
	 */
	 

}
