package com.cooksys.secondassessment.dto;

public class ProfileCredentialDto {
	private CredentialDto credential;
	private ProfileDto profile;

	public CredentialDto getCredential() {
		return credential;
	}

	public void setCredential(CredentialDto credential) {
		this.credential = credential;
	}

	public ProfileDto getProfile() {
		return profile;
	}

	public void setProfile(ProfileDto profile) {
		this.profile = profile;
	}
}
