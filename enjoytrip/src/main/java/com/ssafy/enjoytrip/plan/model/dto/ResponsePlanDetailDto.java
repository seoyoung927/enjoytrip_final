package com.ssafy.enjoytrip.plan.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ResponsePlanDetailDto {
	int pid;
	int contentId;
	String userId;
	String title;
	String overView;
	String addr1;
	List<ResponseCommentDto> myComment;
	List<ResponseCommentDto> otherComment;
	
	public ResponsePlanDetailDto() {
		super();
	}
	
	public ResponsePlanDetailDto(int pid, int contentId, String userId, String title, String overView, String addr1,
			 List<ResponseCommentDto> myComment, List<ResponseCommentDto> otherComment) {
		super();
		this.pid = pid;
		this.contentId = contentId;
		this.userId = userId;
		this.title = title;
		this.overView = overView;
		this.addr1 = addr1;
		this.myComment = myComment;
		this.otherComment = otherComment;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOverView() {
		return overView;
	}
	public void setOverView(String overView) {
		this.overView = overView;
	}

	public List<ResponseCommentDto> getMyComment() {
		return myComment;
	}
	public void setMyComment(List<ResponseCommentDto> myComment) {
		this.myComment = myComment;
	}
	public List<ResponseCommentDto> getOtherComment() {
		return otherComment;
	}
	public void setOtherComment(List<ResponseCommentDto> otherComment) {
		this.otherComment = otherComment;
	}
	
}
