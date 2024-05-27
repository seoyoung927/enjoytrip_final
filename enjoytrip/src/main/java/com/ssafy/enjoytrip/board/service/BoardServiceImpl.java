package com.ssafy.enjoytrip.board.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.board.model.dao.BoardDao;
import com.ssafy.enjoytrip.board.model.dto.Board;
import com.ssafy.enjoytrip.board.model.dto.BoardDetailDTO;
import com.ssafy.enjoytrip.board.model.dto.BoardResponseDTO;
import com.ssafy.enjoytrip.board.model.dto.PageBean;

@Service
public class BoardServiceImpl implements BoardService {
	private BoardDao boardDao;
	public BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}
	
	@Override
	public void insert(Board board) throws Exception{
		boardDao.insert(board);
	}

	@Override
	public BoardDetailDTO search(int boardId) throws Exception{
			return boardDao.search(boardId);
	}

	@Override
	public List<BoardResponseDTO> searchAll(PageBean bean) throws Exception{
		int total = boardDao.totalCount(bean);
		bean.setTotal(total);
		List<BoardResponseDTO>	boards = boardDao.searchAll(bean);
		return boards;
	}

	@Override
	public void update(Board board) throws Exception{
		boardDao.update(board);
	}

	@Override
	public void delete(int boardId) throws Exception{
		boardDao.delete(boardId);
	}

}
