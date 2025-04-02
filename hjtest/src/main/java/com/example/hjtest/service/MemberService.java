package com.example.hjtest.service;


import com.example.hjtest.Dto.MemberDto;
import com.example.hjtest.entity.Member;

public interface MemberService {
    // 회원가입 (회원등록)
    public Member insertMember(MemberDto memberDto);

    // 로그인
    public Member login(MemberDto MemberDto);

    // 회원탈퇴
    public Member deleteMember(String email);
}
