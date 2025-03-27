package com.example.hjtest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "t_Board")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String contents;

    @Column
    private int hitCnt;

    @Column
    private String creatorId;

    @Column
    private LocalDateTime createdDatetime;

    @Column
    private String updaterId;

    @Column
    private LocalDateTime updatedDatetime;

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
        if (updaterId != null && !board.updaterId.trim().isEmpty()){
            this.updaterId=board.updaterId;
        }
    }
}
