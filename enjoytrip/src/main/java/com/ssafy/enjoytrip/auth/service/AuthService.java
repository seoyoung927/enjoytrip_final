package com.ssafy.enjoytrip.auth.service;

import com.ssafy.enjoytrip.auth.model.dto.RequestLoginDto;

public interface AuthService {
    String login(RequestLoginDto requestLoginDto)throws Exception;
    void saveRefreshToken(String userId, String refreshToken) throws Exception;
    Object getRefreshToken(String userId) throws Exception;
    void deleRefreshToken(String userId) throws Exception;
}
