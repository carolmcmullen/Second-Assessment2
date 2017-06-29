package cooksys.secondassessment.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.dto.HashtagDto;
import com.cooksys.secondassessment.mapper.HashtagMapper;
import com.cooksys.secondassessment.service.HashtagService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "hashtag")
public class HashtagController {

	private HashtagService hashtagService;
	private HashtagMapper hashtagMapper;
	
	public HashtagController(HashtagService hashtagService, HashtagMapper hashtagMapper) {
		this.hashtagService = hashtagService;
		this.hashtagMapper = hashtagMapper;
	}
	
	// Creates HashTag	
	//@ApiOperation(value = "/CreateHashTag", nickname = "createNewHashTag")
	@PostMapping("Hashtag")
	public HashtagDto create(@RequestBody HashtagDto hashtag, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_CREATED);
		return hashtagService.createHashtag(hashtagMapper.toHashtag(hashtag));
	}
	
	// Get all HashTags
	@RequestMapping(value = "/getAllHashtags", method = RequestMethod.GET)
	@GetMapping()
	public List<HashtagDto> getAll() {
		return hashtagService.getAll();
	}
	
	/*
	 * TODO: GET tags/{label}
	 */
	
	
}
