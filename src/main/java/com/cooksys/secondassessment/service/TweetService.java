package com.cooksys.secondassessment.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.HashtagDto;
import com.cooksys.secondassessment.dto.TweetDto;
import com.cooksys.secondassessment.dto.UserWithoutIdDto;
import com.cooksys.secondassessment.entity.Hashtag;
import com.cooksys.secondassessment.entity.Mention;
import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.entity.User;
import com.cooksys.secondassessment.mapper.HashtagMapper;
import com.cooksys.secondassessment.mapper.TweetMapper;
import com.cooksys.secondassessment.mapper.UserMapper;
import com.cooksys.secondassessment.repository.HashtagRepository;
import com.cooksys.secondassessment.repository.MentionRepository;
import com.cooksys.secondassessment.repository.TweetRepository;

import javassist.NotFoundException;

@Service
public class TweetService {

	private Logger log = LoggerFactory.getLogger(getClass());
	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;
	private UserService userService;
	private UserMapper userMapper;
	private HashtagMapper hashtagMapper;
	private HashtagService hashtagService;
	private HashtagRepository hashtagRepository;
	private MentionRepository mentionRepository;

	public TweetService(TweetRepository tweetRepository, TweetMapper tweetMapper, UserService userService,
			UserMapper userMapper, HashtagMapper hashtagMapper, MentionRepository mentionRepository,
			HashtagService hashtagService, HashtagRepository hashtagRepository) {
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
		this.userService = userService;
		this.userMapper = userMapper;
		this.hashtagMapper = hashtagMapper;
		this.mentionRepository = mentionRepository;
		this.hashtagService = hashtagService;
		this.hashtagRepository = hashtagRepository;
	}

	public TweetDto createTweet(User user, String content) throws Exception {
		return createTweet(user, content, 0);
	}

	/*
	 * Creates a new simple tweet, with the author set to the user identified by
	 * the credentials in the request body. If the given credentials do not
	 * match an active user in the database, an error should be sent in lieu of
	 * a response.
	 * 
	 * The response should contain the newly-created tweet.
	 * 
	 * Because this always creates a simple tweet, it must have a content
	 * property and may not have inReplyTo or repostOf properties.
	 */
	public TweetDto createTweet(User user, String content, Integer repliedToTweetId) throws Exception {
		User dbUser = userService.checkIfUserIsValid(user);

		if (content == null || content.equals(""))
			throw new Exception("Content can't be blank");

		Tweet tweet = new Tweet();
		tweet.setContent(content);
		tweet.setUser(dbUser);
		tweet.setPosted(new Timestamp(System.currentTimeMillis()));
		tweet.setIsActive(true);

		// search for mentions
		Integer searchIndex = -1;
		Integer mentionIndex = content.indexOf("@");
		while (searchIndex < mentionIndex) {
			Mention mention = new Mention();
			mention.setUsername(content.substring(mentionIndex, content.indexOf(" ", mentionIndex)));
			mention.setTweet(tweet);
			mentionRepository.save(mention);
			searchIndex = mentionIndex;
			mentionIndex = content.indexOf("@") + 1;
		}

		// search for tags
		searchIndex = -1;
		mentionIndex = content.indexOf("#");
		while (searchIndex < mentionIndex) {
			// check if hashtag already exist
			String label = content.substring(mentionIndex, content.indexOf(" ", mentionIndex));
			Hashtag tag = hashtagService.getHashtag(label);
			if (tag == null) {
				tag = new Hashtag();
				tag.setLabel(label);
				tag.setFirstUsed(new Timestamp(System.currentTimeMillis()));
			}

			tag.setLastUsed(new Timestamp(System.currentTimeMillis()));
			tag.setTweet(tweet);
			hashtagRepository.save(tag);
			searchIndex = mentionIndex;
			mentionIndex = content.indexOf("#") + 1;
		}

		if (repliedToTweetId > 0) {
			Tweet repliedToTweet = this.getTweet(repliedToTweetId);
			tweet.setInReplyTo(repliedToTweet);
		}

		Tweet newTweet = tweetRepository.save(tweet);
		return tweetMapper.toTweetDto(newTweet);
	}

	public List<TweetDto> getAll() {
		return tweetRepository.findAll().stream().map(tweetMapper::toTweetDto).collect(Collectors.toList());
	}

	/*
	 * get tweets
	 */
	public Tweet getTweet(Integer id) throws Exception {
		return getActiveTweetById(id);
	}

	/*
	 * Delete tweet
	 */
	public TweetDto deleteTweetById(Integer tweetId) throws NotFoundException {
		Tweet dbTweet = tweetRepository.findOne(tweetId);
		if (dbTweet != null) {
			return tweetMapper.toTweetDto(dbTweet);
		} else {
			throw new NotFoundException("tweet id not valid");
		}
	}

	public void likeTweet(Integer id, User user) throws Exception {
		User dbUser = userService.checkCredentials(user);
		if (dbUser == null)
			throw new Exception();
		Tweet tweet = getActiveTweetById(id);
		List<User> list = new ArrayList<>();
		list.add(dbUser);
		tweet.setUserLikes(list);
	}

	/*
	 * returns users who liked a tweet
	 */
	public List<UserWithoutIdDto> getUsersThatLikedTweet(Integer id) throws Exception {
		Tweet tweet = getActiveTweetById(id);
		return tweet.getUserLikes().stream().filter(u -> u.getIsActive().equals(true))
				.map(userMapper::toUserWithoutIdDto).collect(Collectors.toList());
	}

	public List<HashtagDto> getHashtags(Integer id) throws Exception {
		Tweet tweet = getActiveTweetById(id);
		return tweet.getHashtags().stream().map(hashtagMapper::toHashtagDto).collect(Collectors.toList());
	}

	/*
	 * Creates a reply tweet to the tweet with the given id. The author of the
	 * newly-created tweet should match the credentials provided by the request
	 * body. If the given tweet is deleted or otherwise doesn't exist, or if the
	 * given credentials do not match an active user in the database, an error
	 * should be sent in lieu of a response.
	 * 
	 * Because this creates a reply tweet, content is not optional.
	 * Additionally, notice that the inReplyTo property is not provided by the
	 * request. The server must create that relationship.
	 * 
	 * The response should contain the newly-created tweet.
	 */
	public TweetDto reply(Integer id, User user, String content) throws Exception {
		Tweet tweet = getActiveTweetById(id);
		User dbUser = userService.checkIfUserIsValid(user);

		if (content == null || content.equals(""))
			throw new Exception("Content can't be blank");

		return createTweet(user, content, id);

	}

	private Tweet getActiveTweetById(Integer id) throws Exception {
		Tweet tweet = tweetRepository.findOne(id);
		if (tweet == null || tweet.getIsActive().equals(false))
			throw new Exception("Invalid tweetId");
		return tweet;
	}

	/*
	 * Retrieves all (non-deleted) tweets authored by the user with the given
	 * username. This includes simple tweets, reposts, and replies. The tweets
	 * should appear in reverse-chronological order. If no active user with that
	 * username exists (deleted or never created), an error should be sent in
	 * lieu of a response.
	 */
	public Collection<Tweet> getTweetsByUsername(String username) throws Exception {
		List<Tweet> tweets = tweetRepository.findByUsername(username);

		if (tweets == null || tweets.size() == 0)
			throw new Exception("Invalid username");

		return tweets;
	}

	public Collection<Tweet> getTweetsByMentions(String username) throws Exception {
		List<Mention> mentions = mentionRepository.findByUsername(username);
		List<Tweet> tweets = new ArrayList<Tweet>();
		for (int i = 0; i < mentions.size(); ++i) {
			tweets.add(mentions.get(i).getTweet());
		}

		if (tweets == null || tweets.size() == 0)
			throw new Exception("Invalid username");

		return tweets;
	}
}