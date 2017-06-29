package com.cooksys.secondassessment.dto;

import java.sql.Timestamp;
import com.cooksys.secondassessment.dto.ProfileDto;


public class UserWithoutIdDto {

	private ProfileDto profile;
	private String username;
	private Timestamp Joined;

	public ProfileDto getProfile() {
		return profile;
	}

	public void setProfile(ProfileDto profile) {
		this.profile = profile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getJoined() {
		return Joined;
	}

	public void setJoined(Timestamp joined) {
		Joined = joined;
	}
}
