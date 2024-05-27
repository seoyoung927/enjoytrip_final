package com.ssafy.enjoytrip.auth.controller;

import com.ssafy.enjoytrip.auth.model.dto.RequestTokenDto;
import com.ssafy.enjoytrip.auth.service.AuthService;
import com.ssafy.enjoytrip.auth.model.dto.RequestLoginDto;
import com.ssafy.enjoytrip.auth.util.JWTUtil;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/auth")
@CrossOrigin("*")
@RestController
public class AuthController {
    private final AuthService authService;
    private final JWTUtil jwtUtil;
    public AuthController(AuthService authService, JWTUtil jwtUtil){
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Parameter(required = true) RequestLoginDto requestLoginDto){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            String userId = authService.login(requestLoginDto);
            if(userId != null) {
                String accessToken = jwtUtil.createAccessToken(userId);
                String refreshToken = jwtUtil.createRefreshToken(userId);
                authService.saveRefreshToken(userId, refreshToken);
                resultMap.put("accessToken", accessToken);
                resultMap.put("refreshToken", refreshToken);
                resultMap.put("userId", userId);

                status = HttpStatus.CREATED;
            } else {
                resultMap.put("message", "아이디 또는 패스워드를 확인해 주세요.");
                status = HttpStatus.UNAUTHORIZED;
            }

        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    @GetMapping("/logout/{userId}")
    public ResponseEntity<?> removeToken(@PathVariable ("userId") @Parameter(description = "로그아웃 할 회원의 아이디.", required = true) String userId) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            authService.deleRefreshToken(userId);
            status = HttpStatus.OK;
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RequestTokenDto requestTokenDto, HttpServletRequest request)
            throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        String token = requestTokenDto.getRefreshToken();
        if (jwtUtil.checkToken(token)) {
            String userId = jwtUtil.getUserId(requestTokenDto.getRefreshToken());
            if (token.equals(authService.getRefreshToken(userId))){
                String accessToken = jwtUtil.createAccessToken(userId);
                resultMap.put("accessToken", accessToken);
                status = HttpStatus.CREATED;
            }
        } else {
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
