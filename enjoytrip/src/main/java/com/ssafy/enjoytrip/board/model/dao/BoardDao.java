package com.ssafy.enjoytrip.board.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.board.model.dto.Board;
import com.ssafy.enjoytrip.board.model.dto.BoardDetailDTO;
import com.ssafy.enjoytrip.board.model.dto.BoardResponseDTO;
import com.ssafy.enjoytrip.board.model.dto.PageBean;

@Mapper
public interface BoardDao {
	void insert(Board board) throws SQLException;
	BoardDetailDTO search(int boardId) throws SQLException;
	List<BoardResponseDTO> searchAll(PageBean pageBean) throws SQLException;
	void update(Board board) throws SQLException;
	void delete(int boardId) throws SQLException;
	int totalCount(PageBean pageBean) throws SQLException;
}
