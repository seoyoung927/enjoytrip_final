package com.ssafy.enjoytrip.member.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrip.auth.model.dto.RequestLoginDto;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.member.model.dto.Member;

@Mapper
public interface MemberDao {
	void insert(Member member) throws SQLException;
	List<Member> searchAll() throws SQLException;
	Member search(String userId) throws SQLException;
	void update(Member member) throws SQLException;
	void delete(String userId) throws SQLException;
	String login(RequestLoginDto requestLoginDto) throws SQLException;
}
