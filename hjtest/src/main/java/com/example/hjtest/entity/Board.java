package com.example.hjtest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private Long boardId;

    private String title;

    private String contents;

    private int hitCnt=0;

    @ManyToOne
    @JoinColumn(name = "email")
    private Member member;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDatetime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedDatetime;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments;


    // 최초 생성 시점에 자동으로 갱신
    @PrePersist
    public void prePersist() {
        this.createdDatetime = LocalDateTime.now();  // 생성 시점에 자동으로 시간 갱신
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDatetime = LocalDateTime.now();  // 수정 시점에 자동으로 시간 갱신
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
