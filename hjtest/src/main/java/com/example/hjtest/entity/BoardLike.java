package com.example.hjtest.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "email")
    private Member member;

}
