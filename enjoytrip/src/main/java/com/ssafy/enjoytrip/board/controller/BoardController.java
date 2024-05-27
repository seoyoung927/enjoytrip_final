package com.ssafy.enjoytrip.board.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.board.model.dto.Board;
import com.ssafy.enjoytrip.board.model.dto.BoardDetailDTO;
import com.ssafy.enjoytrip.board.model.dto.BoardException;
import com.ssafy.enjoytrip.board.model.dto.BoardResponseDTO;
import com.ssafy.enjoytrip.board.model.dto.PageBean;
import com.ssafy.enjoytrip.board.service.BoardService;


@RequestMapping("/board")
@CrossOrigin("*")
@RestController
public class BoardController{
	private Logger logger = LoggerFactory.getLogger(BoardController.class);
	private BoardService boardService;
	private static final String SUCCESS="SUCCESS";
	public BoardController(BoardService boardService) {
		System.out.println("BoardController......................................");
		this.boardService = boardService;
	}
    
    @PostMapping()
    public ResponseEntity<?> insert(@RequestBody Board board) throws Exception{
    	boardService.insert(board);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    private ResponseEntity<?> search(@PathVariable("id") int boardId) throws Exception{
		BoardDetailDTO board = boardService.search(boardId);
		if(board!=null) {
			return new ResponseEntity<BoardDetailDTO>(board, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
    }
	
    @GetMapping
    private ResponseEntity<?> searchAll(PageBean bean)  throws Exception {
		List<BoardResponseDTO> boards;
		boards = boardService.searchAll(bean);

		if(boards!=null && boards.size()>0) {
			Map<String, Object> result = new HashMap<>();
			result.put("boards", boards);
			result.put("page", bean);
			return new ResponseEntity<Map>(result, HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
    }
    
    @PatchMapping
    public ResponseEntity<String> update(@RequestBody Board board) throws Exception{
		boardService.update(board);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int boardId) throws Exception{
		boardService.delete(boardId);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
}