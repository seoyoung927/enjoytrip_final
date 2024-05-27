package com.ssafy.enjoytrip.member.exception;

public class DuplicateException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public DuplicateException() {
        super("아이디가 중복됐습니다.");
    }

}
