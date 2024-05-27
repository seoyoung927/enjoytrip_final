package com.ssafy.enjoytrip.plan.model.dto;

import java.time.LocalDateTime;

public class ResponseCommentDto {
	int cid;
	int pid;
	String userId;
	String content;
	boolean isShow;
	LocalDateTime createdTime;

	public ResponseCommentDto() {
	}

	public ResponseCommentDto(int cid, int pid, String userId, String content, boolean isShow, LocalDateTime createdTime) {
		this.cid = cid;
		this.pid = pid;
		this.userId = userId;
		this.content = content;
		this.isShow = isShow;
		this.createdTime = createdTime;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setIsShow(boolean isShow) {
		this.isShow = isShow;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}
}
