package com.example.hjtest.service;

import com.example.hjtest.Dto.board.BoardCreateRequestDto;
import com.example.hjtest.Dto.board.BoardListResponseDto;
import com.example.hjtest.Dto.board.BoardResponseDto;
import com.example.hjtest.Dto.board.BoardUpdateRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {

    List<BoardListResponseDto> selectBoardList();

    BoardListResponseDto selectBoardDetail(int boardId);

    BoardResponseDto insertBoard(BoardCreateRequestDto dto, List<MultipartFile> files);

    BoardResponseDto updateBoard(int boardId, BoardUpdateRequestDto Dto, List<MultipartFile> files);

    boolean deleteBoard(int boardId);
}