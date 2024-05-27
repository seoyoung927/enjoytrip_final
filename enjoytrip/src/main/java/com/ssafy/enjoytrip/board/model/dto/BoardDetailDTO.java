package com.ssafy.enjoytrip.board.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailDTO {
	private int boardId;
	private String title;
	private String userId;
	private LocalDateTime createdAt;
	private String content;
	private String answer;
	
}
