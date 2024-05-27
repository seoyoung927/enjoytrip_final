package com.ssafy.enjoytrip.member.model.dto;

import java.time.LocalDateTime;

public class Member {
	private String userId;
	private String password;
	private String name;
	private String email;
	private String region;
	private boolean isHost;

	//생성자
	public Member() {}


	public Member(String userId, String name, String email, String region, boolean isHost,String password) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.region = region;
		this.password = password;
		this.isHost = isHost;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	


	
	public boolean isHost() {
		return isHost;
	}


	public void setIsHost(boolean isHost) {
		this.isHost = isHost;
	}


	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	
	
}
