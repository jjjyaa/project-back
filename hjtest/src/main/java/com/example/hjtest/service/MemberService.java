package com.example.hjtest.service;


import com.example.hjtest.Dto.member.MemberCreateRequestDto;
import com.example.hjtest.Dto.member.MemberResponseDto;
import com.example.hjtest.Dto.member.MemberUpdateRequestDto;
import com.example.hjtest.entity.Member;

public interface MemberService {
    // 회원가입 (회원등록)
    public Member insertMember(MemberCreateRequestDto memberDto);

    // 로그인
    public Member login(MemberCreateRequestDto MemberDto);

    // 회원수정
    public MemberResponseDto updateMember(String email, MemberUpdateRequestDto dto);
    // 회원탈퇴
    public Member deleteMember(String email);
}
