package cooksys.secondassessment.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.service.HashtagService;
import com.cooksys.secondassessment.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "validate")
public class ValidateController {

	private UserService userService;
	private HashtagService hashtagService;

	public ValidateController(UserService userService, HashtagService hashtagService) {
		this.userService = userService;
		this.hashtagService = hashtagService;
	}

	// Check if username exists
	@RequestMapping(value = "/username/exists/@{username}", method = RequestMethod.GET)
	@ApiOperation(value = "Returns boolean", notes = "Check if username exists")
	public boolean checkIfUsernameExists(@PathVariable String username) {
		return !userService.isUsernameAvailable(username);
	}

	// Check if username is available
	@RequestMapping(value = "/username/available/@{username}", method = RequestMethod.GET)
	public boolean checkIfUsernameAvailable(@PathVariable String username) {
		return userService.isUsernameAvailable(username);
	}

	// Check if hashtag exists
	@RequestMapping(value = "/tag/exists/{label}", method = RequestMethod.GET)
	public boolean checkIfHashtagExists(@PathVariable String label) {
		return hashtagService.checkIfHashTagExist(label);
	}
}
