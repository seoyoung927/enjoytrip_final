package com.ssafy.enjoytrip.auth.model.dto;

public class ResponseMemberDto {
	private String userId;

	public ResponseMemberDto(String userId) {
		super();
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
