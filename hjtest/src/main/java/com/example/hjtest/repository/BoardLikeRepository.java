package com.example.hjtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hjtest.entity.BoardLike;
import com.example.hjtest.entity.Board;
import com.example.hjtest.entity.Member;
import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Integer> {
    Optional<BoardLike> findByBoardAndMember(Board board, Member member);
    int countByBoard(Board board);
}