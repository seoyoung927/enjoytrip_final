package com.ssafy.enjoytrip.plan.model.dto;

import java.time.LocalDateTime;

public class ResponsePlanDto {
	int pid;
	int contentId;
	String userId;
	String title;
	String overview;
	String addr1;
	String imagePath;
	
	public ResponsePlanDto() {
		super();
	}
	
	public ResponsePlanDto(int pid, int contentId, String userId, String title, String overview, String imagePath, String addr1) {
		super();
		this.pid = pid;
		this.contentId = contentId;
		this.userId = userId;
		this.title = title;
		this.overview = overview;
		this.addr1 = addr1;
		this.imagePath = imagePath;
	}
	
	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
