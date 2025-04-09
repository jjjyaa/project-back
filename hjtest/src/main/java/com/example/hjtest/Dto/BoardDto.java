//package com.example.hjtest.Dto;
//
//import com.example.hjtest.entity.Board;
//import com.example.hjtest.entity.BoardFileEntity;
//import lombok.Data;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Data
//public class BoardDto {
//    private int boardId;
//    private String title;
//    private String contents;
//    private int hitCnt;
//    private String email;
//    private String createdDatetime;
//    private String updatedDatetime;
//    private List<CommentDto> comments = new ArrayList<>();
//    // 파일을 받기 위한 필드 추가
//    private List<BoardFileEntity> fileList = new ArrayList<>();
//
//
//    public Board toEntity() {
//        return new Board(boardId, title, contents, hitCnt,null, createdDatetime, updatedDatetime, null,fileList,null);
//    }
//}