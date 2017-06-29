package com.cooksys.secondassessment.dto;

import com.cooksys.secondassessment.entity.Profile;

public class UserWithoutIdDto {

	private Profile profile;
	private String username;
  //private Joined timestamp;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
