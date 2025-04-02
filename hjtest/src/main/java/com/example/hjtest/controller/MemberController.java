package com.example.hjtest.controller;


import com.example.hjtest.Dto.MemberDto;
import com.example.hjtest.entity.Member;
import com.example.hjtest.exception.DuplicateEmailException;
import com.example.hjtest.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(insertmember);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> handleDuplicateEmailException(DuplicateEmailException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Member> login(@RequestBody MemberDto MemberDto) {
        log.info("로그인 요청: {}", MemberDto.toString());
        Member member = memberService.login(MemberDto);
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

}
