package com.ssafy.enjoytrip.auth.model.dto;

public class RequestLoginDto {
	private String userId;
	private String password;
	public RequestLoginDto(String userId, String password){
		this.userId = userId;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
