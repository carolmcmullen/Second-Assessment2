package com.cooksys.secondassessment.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Credentials {

	@Column(unique = true)

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
