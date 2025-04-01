package com.example.hjtest.service;


import com.example.hjtest.entity.Board;
import com.example.hjtest.entity.Comment;
import jakarta.persistence.EntityNotFoundException;
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
    public Member login(MemberDto MemberDto) {
        log.info(MemberDto.toString());
        // 이메일로 사용자 조회
        Optional<Member> optionalMember = memberRepository.findById(MemberDto.getEmail());

        if (optionalMember.isEmpty()) {
            throw new IllegalArgumentException("해당 이메일의 사용자가 없습니다.");
        }

        Member member = optionalMember.get();

        // 비밀번호 일치, 불일치
        if (!member.getPassword().equals(MemberDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return member;
    }

    //회원탈퇴
    public Member deleteMember(String email) {
        Member member = memberRepository.findById(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (member == null){
            log.info("잘못된 아이디"+email);
            return null;
        }
        // 회원의 게시글 및 댓글 삭제
        for (Board board : member.getBoards()) {
            board.setMember(null); // 게시글에서 회원과의 관계를 끊음
        }

        for (Comment comment : member.getComments()) {
            comment.setMember(null); // 댓글에서 회원과의 관계를 끊음
        }
        memberRepository.delete(member);
        return member;
    }
}
