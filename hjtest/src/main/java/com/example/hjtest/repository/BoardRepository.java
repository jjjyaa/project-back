package com.example.hjtest.repository;

import com.example.hjtest.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Integer> {

    //JPQL
    @Modifying
    @Transactional
    @Query("UPDATE Board b SET b.hitCnt = b.hitCnt + 1 WHERE b.id = :boardId")
    int updateHitCount(@Param("boardId") int boardId);

    //생성시간 기준 정렬 (최신글순서)
    @Query("select board from Board board left join fetch board.member order by board.createdDatetime desc")
    List<Board> findAllByOrderByCreatedDatetimeDesc();
}
