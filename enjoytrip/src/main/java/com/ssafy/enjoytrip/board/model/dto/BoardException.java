package com.ssafy.enjoytrip.board.model.dto;

public class BoardException extends RuntimeException {
	public BoardException() {
		super();
	}
	public BoardException(String message) {
		super(message);
	}
}
