package com.ssafy.enjoytrip.plan.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RequestInsertPlanDto {
	int contentId;
	String userId;
	int pid;
	
	public RequestInsertPlanDto() {
		super();
	}


	public RequestInsertPlanDto(int contentId, String userId, int pid) {
		this.contentId = contentId;
		this.userId = userId;
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

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
}
