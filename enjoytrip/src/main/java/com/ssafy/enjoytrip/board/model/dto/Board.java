package com.ssafy.enjoytrip.board.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Board implements Serializable{
	private int boardId;
	private String title;
	private String content;
	private String userId;
	private String answer;
	
	//생성자
	public Board() {}
	

	public Board(int boardId, String title, String content, String userId, String answer, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		super();
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.userId = userId;
		this.answer = answer;
	}


	//getter, setter
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	
}
