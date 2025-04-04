package com.example.hjtest.Dto;

import com.example.hjtest.entity.Board;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
public class BoardDto {
    private int boardId;
    private String title;
    private String contents;
    private int hitCnt;
    private String email;
    private String createdDatetime;
    private String updatedDatetime;
    private List<CommentDto> comments = new ArrayList<>();


    public Board toEntity() {
        return new Board(boardId, title, contents, hitCnt,null, createdDatetime, updatedDatetime, null);
    }
}
