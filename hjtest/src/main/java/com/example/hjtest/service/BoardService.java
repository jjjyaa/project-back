package com.example.hjtest.service;

import com.example.hjtest.Dto.BoardDto;
import com.example.hjtest.entity.Board;
import com.example.hjtest.entity.BoardFileEntity;
import java.util.List;

public interface BoardService {
    List<Board> selectBoardList();

    Board selectBoardDetail(int boardId);

    Board insertBoard(BoardDto boardDto, List<BoardFileEntity> fileList);

    Board updateBoard(int boardId, BoardDto boardDto);

    boolean deleteBorad(int boardId);
}
