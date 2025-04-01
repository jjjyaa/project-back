package com.example.hjtest.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;
    private LocalDateTime createdDatetime;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "member_email")
    private Member member;


}
