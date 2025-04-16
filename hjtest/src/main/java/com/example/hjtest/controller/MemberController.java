package com.example.hjtest.controller;

import com.example.hjtest.Dto.member.MemberCreateRequestDto;
import com.example.hjtest.Dto.member.MemberResponseDto;
import com.example.hjtest.Dto.member.MemberUpdateRequestDto;
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
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 회원가입 (회원등록)
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberCreateRequestDto memberDto) {
        Member saved = memberService.insertMember(memberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MemberResponseDto(saved));
    }

    // 회원수정
    @PatchMapping("/{email}")
    public MemberResponseDto updateMember(@PathVariable String email,
                                          @RequestBody MemberUpdateRequestDto dto) {
        return memberService.updateMember(email, dto);
    }

    // 회원탈퇴
    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteMember(@PathVariable String email) {
        memberService.deleteMember(email);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<MemberResponseDto> login(@RequestBody MemberCreateRequestDto memberDto) {
        Member member = memberService.login(memberDto);
        return ResponseEntity.status(HttpStatus.OK).body(new MemberResponseDto(member));
    }

    @ExceptionHandler({DuplicateEmailException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleExceptions(Exception ex) {
        // DuplicateEmailException 처리
        if (ex instanceof DuplicateEmailException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage()); // DuplicateEmailException 메시지 반환
        }

        // IllegalArgumentException 처리
        if (ex instanceof IllegalArgumentException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()); // IllegalArgumentException 메시지 반환
        }

        // 그 외의 예외는 처리하지 않음 (혹은 기본 처리)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
}
