package cooksys.secondassessment.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.dto.HashtagDto;
import com.cooksys.secondassessment.mapper.HashtagMapper;
import com.cooksys.secondassessment.service.HashtagService;

@RestController
@RequestMapping("tags")
public class HashtagController {

	private HashtagService hashtagService;
	private HashtagMapper hashtagMapper;

	public HashtagController(HashtagService hashtagService, HashtagMapper hashtagMapper) {
		this.hashtagService = hashtagService;
		this.hashtagMapper = hashtagMapper;
	}

	// Creates Hashtag
	// @ApiOperation(value = "/CreateHashTag", nickname = "createNewHashTag")
	@PostMapping("Hashtag")
	public HashtagDto create(@RequestBody HashtagDto hashtag, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_CREATED);
		return hashtagService.createHashtag(hashtagMapper.toHashtag(hashtag));
	}

	@RequestMapping(value = "/getAllHashtags", method = RequestMethod.GET)
	@GetMapping()
	public List<HashtagDto> getAll() {
		return hashtagService.getAll();
	}

	@RequestMapping(value = "/{label}", method = RequestMethod.GET)
	@GetMapping()
	public HashtagDto getHashtagByLabel(@PathVariable String label, HttpServletResponse response) {
		return hashtagMapper.toHashtagDto(hashtagService.getHashtag(label));
	}
}
