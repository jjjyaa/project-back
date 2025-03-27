package com.example.hjtest.repository;

import com.example.hjtest.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 이메일로 회원 찾기 : 로그인/중복체크
    Optional<Member> findByEmail(String email);
}
