package com.cooksys.secondassessment.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.entity.User;

public class TweetDto {
	private Integer id;
	private User user;
	private Timestamp posted;
	
	private String content;
	
	private Tweet inReplyTo;
	
	private Tweet repostOf;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Timestamp getPosted() {
		return posted;
	}
	public void setPosted(Timestamp posted) {
		this.posted = posted;
	}
	public String getContent() {
		return content;
	}	
	
	public void setContent(String content) {
		this.content = content;
	}
	public Tweet getInReplyTo() {
		return inReplyTo;
	}
	public void setInReplyTo(Tweet inReplyTo) {
		this.inReplyTo = inReplyTo;
	}
	public Tweet getRepostOf() {
		return repostOf;
	}
	public void setRepostOf(Tweet repostOf) {
		this.repostOf = repostOf;
	}
}
