package com.example.hjtest.service;

import com.example.hjtest.Dto.BoardCreateRequestDto;
import com.example.hjtest.Dto.BoardDto;
import com.example.hjtest.Dto.BoardResponseDto;
import com.example.hjtest.entity.Board;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    List<Board> selectBoardList();

    Board selectBoardDetail(int boardId);

    public BoardResponseDto insertBoard(BoardCreateRequestDto dto, List<MultipartFile> files);

    Board updateBoard(int boardId, BoardDto boardDto);

    boolean deleteBorad(int boardId);
}