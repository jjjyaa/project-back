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

    private int commentCount;
    private int likeCount;

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

    // MyBatis용 생성자 (쿼리 결과 매핑용)
    public BoardListResponseDto(
            int boardId,
            String title,
            int hitCnt,
            String name,
            String createdDatetime,
            int commentCount,
            int likeCount
    ) {
        this.boardId = boardId;
        this.title = title;
        this.hitCnt = hitCnt;
        this.name = name;
        this.createdDatetime = createdDatetime;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
    }
}