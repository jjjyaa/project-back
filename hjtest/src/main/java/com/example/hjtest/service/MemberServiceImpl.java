package com.example.hjtest.service;

import com.example.hjtest.Dto.LoginDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hjtest.Dto.MemberDto;
import com.example.hjtest.entity.Member;
import com.example.hjtest.repository.MemberRepository;

import java.util.Optional;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // 회원가입 (회원등록)
    @Override
    public Member insertMember(MemberDto memberDto) {
        log.info(memberDto.toString());
        // DTO → Entity 변환
        Member member = memberDto.toEntity();
        // INSERT 처리
        return memberRepository.save(member);
    }

    // 로그인
    public Member login(LoginDto loginDto) {
        log.info(loginDto.toString());
        // 이메일로 사용자 조회
        Optional<Member> optionalMember = memberRepository.findByEmail(loginDto.getEmail());

        if (optionalMember.isEmpty()) {
            throw new IllegalArgumentException("해당 이메일의 사용자가 없습니다.");
        }

        Member member = optionalMember.get();

        // 비밀번호 일치, 불일치
        if (!member.getPassword().equals(loginDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return member;
    }
}
