package com.example.hjtest.Dto.board;


import com.example.hjtest.entity.Board;
import com.example.hjtest.entity.BoardFileEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class BoardListResponseDto {
    private int boardId;
    private String title;
    private int hitCnt;
    private String name;
    private String createdDatetime;
    private String contents;
    private List<BoardFileEntity> files;

    // 생성자 방식
    public BoardListResponseDto(Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.hitCnt = board.getHitCnt();
        this.name = board.getMember().getName();
        this.createdDatetime = board.getCreatedDatetime();
        this.contents = board.getContents();
        this.files = board.getFileList();
    }
}