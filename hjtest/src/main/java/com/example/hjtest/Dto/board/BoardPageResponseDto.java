package com.example.hjtest.Dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class BoardPageResponseDto {
    private List<BoardListResponseDto> content;
    private int totalElements;
    private int totalPages;
    private int size;
    private int number;
    private int numberOfElements;
}
