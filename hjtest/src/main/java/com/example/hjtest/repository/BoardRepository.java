package com.example.hjtest.repository;

import com.example.hjtest.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface BoardRepository extends JpaRepository<Board,Long> {
    @Override
    ArrayList<Board> findAll();

}
