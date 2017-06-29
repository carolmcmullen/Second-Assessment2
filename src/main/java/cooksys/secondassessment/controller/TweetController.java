package cooksys.secondassessment.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.dto.TweetDto;
import com.cooksys.secondassessment.mapper.TweetMapper;
import com.cooksys.secondassessment.service.TweetService;

import io.swagger.annotations.Api;

@RestController
@Api(value = "tweet")
public class TweetController {

	private TweetService tweetService;
	private TweetMapper tweetMapper;
	
	public TweetController(TweetService tweetService, TweetMapper tweetMapper) {
		this.tweetService = tweetService;
		this.tweetMapper = tweetMapper;
	}
	
	// Creates HashTag
	
	//@ApiOperation(value = "/CreateHashTag", nickname = "createNewHashTag")
	@PostMapping("Tweet")
	public TweetDto create(@RequestBody TweetDto tweet, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_CREATED);
		return tweetService.createTweet(tweetMapper.toTweet(tweet));
	}
	
	// Get all HashTags
	@RequestMapping(value = "/getAllTweets", method = RequestMethod.GET)
	@GetMapping()
	public List<TweetDto> getAll() {
		return tweetService.getAll();
	}
}
