package com.cooksys.secondassessment.dto;

public class ReplyDto {
	private CredentialDto credential;
	
	private String content;

	public CredentialDto getCredential() {
		return credential;
	}

	public void setCredential(CredentialDto credential) {
		this.credential = credential;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
