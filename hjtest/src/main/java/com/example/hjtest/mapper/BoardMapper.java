package com.example.hjtest.mapper;

import com.example.hjtest.Dto.board.BoardListResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface BoardMapper {
    List<BoardListResponseDto> searchBoards(
            @Param("searchTerm") String searchTerm,
            @Param("searchType") String searchType,
            @Param("offset") int offset,
            @Param("size") int size
    );

    int countBoards(
            @Param("searchTerm") String searchTerm,
            @Param("searchType") String searchType
    );
}