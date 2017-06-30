package cooksys.secondassessment.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.dto.CredentialDto;
import com.cooksys.secondassessment.dto.HashtagDto;
import com.cooksys.secondassessment.dto.ReplyDto;
import com.cooksys.secondassessment.dto.TweetDto;
import com.cooksys.secondassessment.dto.UserWithoutIdDto;
import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.mapper.TweetMapper;
import com.cooksys.secondassessment.mapper.UserMapper;
import com.cooksys.secondassessment.service.TweetService;

import io.swagger.annotations.Api;
import javassist.NotFoundException;

@RestController
@RequestMapping("tweets")
public class TweetController {

	private TweetService tweetService;
	private TweetMapper tweetMapper;
	private UserMapper userMapper;

	public TweetController(TweetService tweetService, TweetMapper tweetMapper, UserMapper userMapper) {
		this.tweetService = tweetService;
		this.tweetMapper = tweetMapper;
		this.userMapper = userMapper;
	}

	// @ApiOperation(value = "/CreateHashTag", nickname = "createNewHashTag")
	@PostMapping()
	public TweetDto create(@RequestBody ReplyDto reply, HttpServletResponse response) throws Exception {
		response.setStatus(HttpServletResponse.SC_CREATED);
		return tweetService.createTweet(userMapper.toUser(reply.getCredential()), reply.getContent());
	}

	// Get all Tweets
	@RequestMapping(value = "/getAllTweets", method = RequestMethod.GET)
	@GetMapping()
	public List<TweetDto> getAll() {
		return tweetService.getAll();
	}

	// Get tweets
	@GetMapping("{id}")
	public TweetDto getTweet(@PathVariable Integer id, HttpServletResponse response) throws Exception {
		try {
			return tweetMapper.toTweetDto(tweetService.getTweet(id));
		} catch (NotFoundException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw e;
		}
	}

	@DeleteMapping("{id}")
	public TweetDto deleteTweetById(@PathVariable Integer id, HttpServletResponse response) throws NotFoundException {
		try {
			return tweetService.deleteTweetById(id);
		} catch (NotFoundException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw e;
		}
	}

	@PostMapping("{id}/like")
	public void addTweetLike(@PathVariable Integer id, @RequestBody CredentialDto user, HttpServletResponse response)
			throws Exception {
		try {
			tweetService.likeTweet(id, userMapper.toUser(user));
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw e;
		}
	}

	@GetMapping("{id}/likes")
	public List<UserWithoutIdDto>getTweetLike(@PathVariable Integer id, HttpServletResponse response)
			throws Exception {
		try {
			return tweetService.getUsersThatLikedTweet(id);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw e;
		}
	}
		
	 @GetMapping("{id}/tags")
	 public List<HashtagDto> getHashtag(@PathVariable Integer id, HttpServletResponse response)
				throws Exception {
			try {
				return tweetService.getHashtags(id);
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				throw e;
			}
		}
	 

	 @PostMapping("{id}/reply")
	 public TweetDto reply(@PathVariable Integer id, @RequestBody ReplyDto reply, HttpServletResponse response)
			throws Exception {
		try {
			return tweetService.reply(id, userMapper.toUser(reply.getCredential()), reply.getContent());
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw e;
		}
	 }

	/*
	 * TODO: POST tweets/{id}/replies
	 * 
	 * @PostMapping("{id}/replies")
	 */

	/*
	 * TODO: POST tweets/{id}/repost
	 * 
	 * @PostMapping("{id}/repost")
	 */

	/*
	 * TODO: POST tweets/{id}/reposts
	 * 
	 * @PostMapping("{id}/reposts")
	 */

	

	/*
	 * TODO: POST tweets/{id}/mentions
	 * 
	 * @PostMapping("{id}/mentions")
	 */

	/*
	 * TODO: POST tweets/{id}/context
	 * 
	 * @PostMapping("{id}/context")
	 */
}
