package com.example.hjtest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "t_board")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardId;

    private String title;

    private String contents;

    private int hitCnt=0;

    @ManyToOne
    @JoinColumn(name = "email")
    @JsonManagedReference
    private Member member;

    private String createdDatetime;

    private String updatedDatetime;

    @JsonBackReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments;

    // 게시글 삭제 시, 관련 파일도 삭제
    @JsonManagedReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardFileEntity> fileList = new ArrayList<>();

    // 게시글 좋아요
    @JsonBackReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardLike> likes = new ArrayList<>();

    // 최초 생성 시점에 자동으로 갱신
    @PrePersist
    public void prePersist() {
        // LocalDateTime을 원하는 형식의 String으로 변환하여 저장
        this.createdDatetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    @PreUpdate
    public void preUpdate() {
        // 수정 시점에 자동으로 시간 갱신 (String으로 변환)
        this.updatedDatetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
    public void patchCheck(Board board){
        if (title != null && !board.title.trim().isEmpty()){
            this.title=board.title;
        }
        if (contents != null && !board.contents.trim().isEmpty()){
            this.contents=board.contents;
        }
    }

}
