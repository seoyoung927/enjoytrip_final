package com.ssafy.enjoytrip.member.service;

import java.util.List;

import com.ssafy.enjoytrip.member.model.dto.Member;

public interface MemberService {
	void signup(Member member) throws Exception;
	List<Member> searchAll() throws Exception;
	Member search(String userId) throws Exception;
	Member update(Member member) throws Exception;
	void delete(String userId) throws Exception;
	
}
