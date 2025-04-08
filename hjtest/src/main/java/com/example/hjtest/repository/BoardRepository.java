package com.example.hjtest.repository;

import com.example.hjtest.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Integer> {

    //JPQL
    @Modifying
    @Transactional
    @Query("UPDATE Board b SET b.hitCnt = b.hitCnt + 1 WHERE b.id = :boardId")
    int updateHitCount(@Param("boardId") int boardId);

    @Query("SELECT DISTINCT board FROM Board board " +
            "LEFT JOIN FETCH board.member " +
            "LEFT JOIN FETCH board.fileList " +
            "ORDER BY board.boardId DESC")
    List<Board> findAllByOrderByBoardIdDesc();

    @Query("SELECT DISTINCT b FROM Board b " +
            "LEFT JOIN FETCH b.member " +
            "LEFT JOIN FETCH b.fileList " +
            "WHERE b.boardId = :boardId")
    Optional<Board> findDetailById(@Param("boardId") int boardId);
}
