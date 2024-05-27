package com.ssafy.enjoytrip.hotplace.model.dto;

public class RequestHotPlaceCommentDto {
    int cid;
    String userId;
    String content;
    int hid;
    
    public RequestHotPlaceCommentDto() {
		super();
	}
	
	public RequestHotPlaceCommentDto(int cid, String userId, String content, int hid) {
		super();
		this.cid = cid;
		this.userId = userId;
		this.content = content;
		this.hid = hid;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
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
	public int getHid() {
		return hid;
	}
	public void setHid(int hid) {
		this.hid = hid;
	}

}
