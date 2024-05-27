package com.ssafy.enjoytrip.auth.service;

import com.ssafy.enjoytrip.auth.model.dao.AuthDao;
import com.ssafy.enjoytrip.auth.model.dto.RequestLoginDto;
import com.ssafy.enjoytrip.member.model.dao.MemberDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements  AuthService{

    private MemberDao memberDao;
    private AuthDao authDao;

    public AuthServiceImpl(MemberDao memberDao, AuthDao authDao) {
        this.memberDao = memberDao;
        this.authDao = authDao;
    }

    @Transactional
    public void saveRefreshToken(String userId, String refreshToken) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", refreshToken);
        authDao.saveRefreshToken (map);
    }



    @Transactional
    public Object getRefreshToken(String userId) throws Exception {
        return authDao.getRefreshToken(userId);
    }

    @Transactional
    public void deleRefreshToken(String userId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("token", null);
        authDao.deleteRefreshToken(map);
    }



    @Override
    public String login(RequestLoginDto requestLoginDto) throws Exception {
        // TODO Auto-generated method stub
        return authDao.login(requestLoginDto);
    }

}
