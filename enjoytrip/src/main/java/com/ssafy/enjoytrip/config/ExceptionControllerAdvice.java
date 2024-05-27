package com.ssafy.enjoytrip.config;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ssafy.enjoytrip.auth.exception.UnAuthorizedException;
import com.ssafy.enjoytrip.member.exception.DuplicateException;

@ControllerAdvice
public class ExceptionControllerAdvice {
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<String> sqlException(){
		return new ResponseEntity<>("sqlError", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseEntity<String> unAuthorizedException(){
		return new ResponseEntity<>("unAutorizedError", HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(DuplicateException.class)
	public ResponseEntity<String> duplicateException(){
		return new ResponseEntity<>("아이디가 중복됐습니다.", HttpStatus.BAD_REQUEST);
	}
}
