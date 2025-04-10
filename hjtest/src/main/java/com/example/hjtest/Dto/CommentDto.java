//package com.example.hjtest.Dto;
//
//import com.example.hjtest.entity.Board;
//import com.example.hjtest.entity.Comment;
//import com.example.hjtest.entity.Member;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class CommentDto {
//    private int commentId;
//    private String content;
//    private String createdDatetime;
//    private int boardId;
//    private String email;
//
//    public Comment toEntity(Board board, Member member) {
//        return new Comment(this.commentId, this.content, this.createdDatetime, board, member);
//    }
//}
