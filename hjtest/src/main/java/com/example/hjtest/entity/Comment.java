package com.example.hjtest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    private String content;

    private String createdDatetime;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "email")
    private Member member;

    @PrePersist
    public void prePersist() {
        // 생성 시점에 자동으로 시간 갱신 (String으로 변환하여 저장)
        this.createdDatetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
