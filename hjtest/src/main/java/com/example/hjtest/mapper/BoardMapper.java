package com.example.hjtest.mapper;


import com.example.hjtest.Dto.board.BoardListResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface BoardMapper {
    List<BoardListResponseDto> searchBoards(String searchTerm, String searchType, int offset, int size);

    int countBoards(String searchTerm, String searchType);
}