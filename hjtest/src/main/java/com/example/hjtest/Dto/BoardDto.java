package com.example.hjtest.Dto;

import com.example.hjtest.entity.Board;
import com.example.hjtest.entity.Comment;
import com.example.hjtest.entity.Member;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BoardDto {
    private Long boardId;
    private String title;
    private String contents;
    private int hitCnt;
    private MemberDto member;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;
    private List<CommentDto> comments = new ArrayList<>();


    public Board toEntity() {
        return new Board(boardId, title, contents, hitCnt,null, createdDatetime, updatedDatetime, null);
    }
}
