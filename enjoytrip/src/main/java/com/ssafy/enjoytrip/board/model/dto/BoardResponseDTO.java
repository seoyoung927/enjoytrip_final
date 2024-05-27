package com.ssafy.enjoytrip.board.model.dto;

import java.time.LocalDateTime;

public class BoardResponseDTO {
	private int boardId;
	private String title;
	private String userId;
	private LocalDateTime createdAt;
	
	public BoardResponseDTO() {
		super();
	}
	public BoardResponseDTO(int boardId, String title, String userId, LocalDateTime createdAt) {
		super();
		this.boardId = boardId;
		this.title = title;
		this.userId = userId;
		this.createdAt = createdAt;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	
	
}
