package com.example.hjtest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "board_like")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference("boardLikeRef") // 게시글 좋아요 → Board
    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    @JsonBackReference("memberLikeRef") // 좋아요한 사람(Member)
    @ManyToOne
    @JoinColumn(name = "email")
    private Member member;

}
