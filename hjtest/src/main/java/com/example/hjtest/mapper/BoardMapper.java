package com.example.hjtest.mapper;


import com.example.hjtest.Dto.board.BoardListResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
public interface BoardMapper {
    List<BoardListResponseDto> searchBoards(String searchTerm, String searchType);
}