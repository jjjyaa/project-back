package com.example.hjtest.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class Member {

    @Id // 기본키
    private String email;

    private String password;

    private String name;

    private String phone;

    private String address;

    @Builder.Default
    @JsonManagedReference("boardWriter") // 게시글 작성자 → Board
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    @Builder.Default
    @JsonManagedReference("commentWriter") // 댓글 작성자 → Comment
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @JsonManagedReference("memberLikeRef") // 좋아요한 게시글 → BoardLike
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardLike> likes = new ArrayList<>();

}


