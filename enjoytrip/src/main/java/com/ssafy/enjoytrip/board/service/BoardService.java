package com.ssafy.enjoytrip.board.service;

import java.util.List;

import com.ssafy.enjoytrip.board.model.dto.Board;
import com.ssafy.enjoytrip.board.model.dto.BoardDetailDTO;
import com.ssafy.enjoytrip.board.model.dto.BoardResponseDTO;
import com.ssafy.enjoytrip.board.model.dto.PageBean;

public interface BoardService {
	void insert(Board board) throws Exception;
	BoardDetailDTO search(int boardId) throws Exception;
	List<BoardResponseDTO> searchAll(PageBean bean) throws Exception;
	void update(Board board) throws Exception;
	void delete(int boardId) throws Exception;
}
