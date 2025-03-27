package com.example.hjtest.service;

import com.example.hjtest.Dto.BoardDto;
import com.example.hjtest.entity.Board;

import java.util.List;

public interface BoardService {
    List<Board> selectBoardList();

    Board selectBoardDetail(Long boardId);

    Board insertBoard(BoardDto boardDto,String email);

    Board updateBoard(Long boardId, BoardDto boardDto);

    boolean deleteBorad(Long boardId);

    void incrementHitCount(Long boardId);
}
