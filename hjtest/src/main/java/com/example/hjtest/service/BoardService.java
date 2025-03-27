package com.example.hjtest.service;

import com.example.hjtest.Dto.BoardDto;
import com.example.hjtest.entity.Board;

import java.util.List;

public interface BoardService {
    List<Board> selectBoardList();

    Board selectBoardDetail(Long id);

    Board insertBoard(BoardDto boardDto);

    Board updateBoard(Long id, BoardDto boardDto);

    boolean deleteBorad(Long id);

}
