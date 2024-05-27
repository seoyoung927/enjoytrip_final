package com.ssafy.enjoytrip.product.model.dto;

public class BookDto {
	private int bid;
	private String userId;
	private int pid;
	private String memo;

	public BookDto() {
	}
	public BookDto(int bid, String userId, int pid, String memo) {
		super();
		this.bid = bid;
		this.userId = userId;
		this.pid = pid;
		this.memo = memo;
	}
	
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}	
}
