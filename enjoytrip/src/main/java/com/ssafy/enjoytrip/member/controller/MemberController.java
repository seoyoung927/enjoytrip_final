package com.ssafy.enjoytrip.member.controller;



import java.util.List;

import com.ssafy.enjoytrip.member.exception.DuplicateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.ssafy.enjoytrip.member.model.dto.Member;
import com.ssafy.enjoytrip.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/member")
@CrossOrigin("*")
@RestController
public class MemberController{
	private final MemberService memberService;
	
	@Autowired
    public MemberController(MemberService memberService) {
    	this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<?> getUserList(HttpServletRequest request) throws Exception{
        String userId = (String)request.getAttribute("userId");
    	Member member = memberService.search(userId);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody Member member) throws Exception{
        memberService.signup(member);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PatchMapping
    public ResponseEntity<Member> update(@RequestBody Member member) throws Exception{
        Member updatedMember = memberService.update(member);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> delete(@PathVariable String userId) throws Exception{
        memberService.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


	

}
