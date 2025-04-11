package com.example.hjtest.Dto.comment;

import com.example.hjtest.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentListResponseDto {
    private int commentId;
    private String content;
    private String createdDatetime;
    private int boardId;
    private String name;

    public CommentListResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.createdDatetime = comment.getCreatedDatetime();
        this.boardId = comment.getBoard().getBoardId();
        this.name = comment.getMember().getName();
    }
}
