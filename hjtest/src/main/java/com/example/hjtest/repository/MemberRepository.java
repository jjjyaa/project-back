package com.example.hjtest.repository;

import com.example.hjtest.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

}
