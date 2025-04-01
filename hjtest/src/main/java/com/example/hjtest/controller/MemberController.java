package com.example.hjtest.controller;

import com.example.hjtest.Dto.LoginDto;
import com.example.hjtest.Dto.MemberDto;
import com.example.hjtest.entity.Member;
import com.example.hjtest.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 회원가입 (회원등록)
    @PostMapping("/signup")
    public ResponseEntity<Member> signup(@RequestBody MemberDto memberDto) {
        log.info("회원가입 요청: {}", memberDto.toString());
        Member insertmember = memberService.insertMember(memberDto);
        return ResponseEntity.ok(insertmember);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Member> login(@RequestBody MemberDto MemberDto) {
        log.info("로그인 요청: {}", MemberDto.toString());
        Member member = memberService.login(MemberDto);
        return ResponseEntity.ok(member);
    }

}
