package com.ssafy.enjoytrip.auth.model.dao;

import java.sql.SQLException;
import java.util.Map;

import com.ssafy.enjoytrip.auth.model.dto.RequestLoginDto;
import com.ssafy.enjoytrip.member.model.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthDao {
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userid) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	String login(RequestLoginDto requestLoginDto) throws SQLException;
	void signup(Member member) throws SQLException;
	void deleteAuth(String userId) throws SQLException;
}
