package cooksys.secondassessment.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.dto.UserWithIdDto;
import com.cooksys.secondassessment.dto.UserWithoutIdDto;
import com.cooksys.secondassessment.entity.User;
import com.cooksys.secondassessment.mapper.UserMapper;
import com.cooksys.secondassessment.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "user")
public class UserController {

	private UserService userService;
	private UserMapper userMapper;
	
	public UserController(UserService userService, UserMapper userMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
	}
	
	// Creates User
	
	//@ApiOperation(value = "/CreateUser", nickname = "createNewUser")
	@PostMapping("User")
	public UserWithIdDto create(@RequestBody UserWithoutIdDto user, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_CREATED);
		return userService.createUser(userMapper.toUser(user));
	}
	
	// Get all Users
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	//@ApiOperation(value = "get all users", nickname = "getAllUsers")
	@GetMapping()
	public List<UserWithIdDto> getAll() {
		return userService.getAll();
	}
}
