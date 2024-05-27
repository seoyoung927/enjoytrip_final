package com.ssafy.enjoytrip.auth.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.enjoytrip.auth.exception.UnAuthorizedException;
import com.ssafy.enjoytrip.auth.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTInterceptor implements HandlerInterceptor {

	private final String HEADER_AUTH = "Authorization";
	
	private JWTUtil jwtUtil;

	public JWTInterceptor(JWTUtil jwtUtil) {
		super();
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(CorsUtils.isPreFlightRequest(request))
			return true;
		final String token = request.getHeader(HEADER_AUTH);
		if (token != null && jwtUtil.checkToken(token)) {
			request.setAttribute("userId", jwtUtil.getUserId(token));
			return true;
		} else {
			throw new UnAuthorizedException();
		}

	}
}
