package cooksys.secondassessment.controller;

import java.util.List;

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
import com.cooksys.secondassessment.dto.UserWithIdDto;
import com.cooksys.secondassessment.dto.UserWithoutIdDto;
import com.cooksys.secondassessment.entity.User;
import com.cooksys.secondassessment.mapper.ProfileMapper;
import com.cooksys.secondassessment.mapper.UserMapper;
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

	public UserController(UserService userService, UserMapper userMapper, ProfileMapper profileMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
		this.profileMapper = profileMapper;
	}

	// Creates User

	@ApiOperation(value = "/CreateUser", nickname = "createNewUser")
	@PostMapping()
	public UserWithIdDto create(@RequestBody CredentialDto user, HttpServletResponse response) {
		// todo: If any required fields are missing or the username provided is already taken, an error should be sent in lieu of a response.
			//If the given credentials match a previously-deleted user, re-activate the deleted user instead of creating a new one.
		response.setStatus(HttpServletResponse.SC_CREATED);
		return userService.createUser(userMapper.toUser(user));
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
		public UserWithoutIdDto getUser(@PathVariable String username, HttpServletResponse response) throws NotFoundException {
			try {
				return userService.getUser(username);
			} catch (NotFoundException e) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				throw e;
			}
		}

	// Delete user
	@DeleteMapping("@{username}")
	public UserWithoutIdDto delete(@RequestBody CredentialDto user, HttpServletResponse response)
			throws NotFoundException {
		try {
			return userService.deleteUser(userMapper.toUser(user));
		} catch (NotFoundException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw e;
		}
	}

	@PatchMapping("@{username}")
	public UserWithoutIdDto UpdateUser(@RequestBody ProfileCredentialDto profileCredential, HttpServletResponse response)
			throws NotFoundException {
		try {
			return userService.updateProfile(userMapper.toUser(profileCredential.getCredential()),
					profileMapper.toProfile(profileCredential.getProfile()));
		} catch (NotFoundException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw e;
		}
	}
	
	/*
	 * TODO: POST users/@{username}/follow
	 */
	
	/*
	 * TODO: POST users/@{username}/follow
	 */
	
	/*
	 * TODO: GET users/@{username}/feed
	 */
	
	/*
	 * TODO: GET users/@{username}/tweets
	 */
	
	/*
	 * TODO: POST users/@{username}/mentions
	 */
	
	/*
	 * TODO: GET users/@{username}/followers
	 */
	
	/*
	 * TODO: GET users/@{username}/following
	 */
}
