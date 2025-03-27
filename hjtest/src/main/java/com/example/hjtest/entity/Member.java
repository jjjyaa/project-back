package com.example.hjtest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// Entity 는 DB 테이블과 매핑
// DB에 실제로 저장될 사용자 정보
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id // 기본키
    private String email;

    private String password;

    private String name;

    private String phone;

    private String address;

    @OneToMany(mappedBy = "member")  // Member가 작성한 게시글
    private List<Board> boards;

    @OneToMany(mappedBy = "member")  // Member가 작성한 댓글
    private List<Comment> comments;
}


