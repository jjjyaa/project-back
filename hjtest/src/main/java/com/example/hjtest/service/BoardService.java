package com.example.hjtest.service;

import com.example.hjtest.Dto.board.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {

    public BoardListResponseDto selectBoardDetail(int boardId);

    public BoardResponseDto insertBoard(BoardCreateRequestDto dto, List<MultipartFile> files);

    public BoardResponseDto updateBoard(int boardId, BoardUpdateRequestDto Dto, List<MultipartFile> files);

    public boolean deleteBoard(int boardId);

    public BoardPageResponseDto getPagedBoards(String searchTerm, String searchType, int page, int size);

}