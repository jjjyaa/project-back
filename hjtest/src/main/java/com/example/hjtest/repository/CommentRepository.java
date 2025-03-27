package com.example.hjtest.repository;

import com.example.hjtest.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Override
    ArrayList<Comment> findAll();
}
