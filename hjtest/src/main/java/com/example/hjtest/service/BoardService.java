package com.example.hjtest.service;

import com.example.hjtest.Dto.board.BoardCreateRequestDto;
import com.example.hjtest.Dto.board.BoardListResponseDto;
import com.example.hjtest.Dto.board.BoardResponseDto;
import com.example.hjtest.Dto.board.BoardUpdateRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {

    public List<BoardListResponseDto> selectBoardList();

    public BoardListResponseDto selectBoardDetail(int boardId);

    public BoardResponseDto insertBoard(BoardCreateRequestDto dto, List<MultipartFile> files);

    public BoardResponseDto updateBoard(int boardId, BoardUpdateRequestDto Dto, List<MultipartFile> files);

    public boolean deleteBoard(int boardId);

    public List<BoardListResponseDto> searchBoards(String searchTerm, String searchType);

}