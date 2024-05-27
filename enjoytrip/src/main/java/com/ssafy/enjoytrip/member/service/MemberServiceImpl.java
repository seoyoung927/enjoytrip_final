package com.ssafy.enjoytrip.member.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrip.auth.model.dao.AuthDao;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.member.exception.DuplicateException;
import com.ssafy.enjoytrip.member.model.dao.MemberDao;
import com.ssafy.enjoytrip.member.model.dto.Member;

import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService{

	private final MemberDao memberDao;
	private final AuthDao authDao;

	public MemberServiceImpl(MemberDao memberDao, AuthDao authDao) {
		this.memberDao = memberDao;
		this.authDao = authDao;
	}
	
	@Transactional
	public void signup(Member member) throws Exception {
		Member duplicate = memberDao.search(member.getUserId());
		if(duplicate!=null) throw new DuplicateException();
		memberDao.insert(member);
		authDao.signup(member);
	}

	@Transactional
	public List<Member> searchAll() throws Exception{
		List<Member> members = memberDao.searchAll();
		return members;
	}

	@Transactional
	public Member search(String userId)throws Exception { 
		return memberDao.search(userId);
	}

	@Transactional
	public Member update(Member member) throws Exception{
		memberDao.update(member);
		return member;
	}

	@Transactional
	public void delete(String userId) throws Exception {
		memberDao.delete(userId);
	}

}
