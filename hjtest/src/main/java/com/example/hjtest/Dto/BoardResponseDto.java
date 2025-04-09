package com.example.hjtest.Dto;

import com.example.hjtest.entity.Board;
import lombok.Data;

@Data
public class BoardResponseDto {
    private int boardId;
    private String title;
    private String contents;
    private String createdDatetime;
    private String writerName;

    public static BoardResponseDto fromEntity(Board board) {
        BoardResponseDto dto = new BoardResponseDto();
        dto.setBoardId(board.getBoardId());
        dto.setTitle(board.getTitle());
        dto.setContents(board.getContents());
        dto.setCreatedDatetime(board.getCreatedDatetime());
        dto.setWriterName(board.getMember().getName());
        return dto;
    }
}
