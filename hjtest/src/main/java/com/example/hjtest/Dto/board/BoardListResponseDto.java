package com.example.hjtest.Dto.board;


import com.example.hjtest.entity.Board;
import lombok.Data;

@Data
public class BoardListResponseDto {
    private int boardId;
    private String title;
    private int hitCnt;
    private String name;
    private String createdDatetime;
    private String contents;

    // 생성자 방식
    public BoardListResponseDto(Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.hitCnt = board.getHitCnt();
        this.name = board.getMember().getName();
        this.createdDatetime = board.getCreatedDatetime();
        this.contents = board.getContents();
    }
}