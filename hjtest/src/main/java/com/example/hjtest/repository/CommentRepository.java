package com.example.hjtest.repository;

import com.example.hjtest.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    @Override
    ArrayList<Comment> findAll();

    List<Comment> findAllByBoardBoardId(int boardId);
}
