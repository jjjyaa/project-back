package com.example.hjtest.Dto;

import com.example.hjtest.entity.Board;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDto {
    private Long id;
    private String title;
    private String contents;
    private int hitCnt;
    private String creatorId;
    private LocalDateTime createdDatetime;
    private String updaterId;
    private LocalDateTime updatedDatetime;

    public Board toEntity(){
        return new Board(id,title,contents,hitCnt,creatorId,createdDatetime,updaterId,updatedDatetime);
    }
}
