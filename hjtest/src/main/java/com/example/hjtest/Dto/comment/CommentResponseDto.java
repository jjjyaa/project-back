package com.example.hjtest.Dto.comment;

import com.example.hjtest.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private int commentId;
    private String content;
    private String createdDatetime;
    private int boardId;
    private String email;

    public static CommentResponseDto fromEntity(Comment comment) {
        return new CommentResponseDto(
                comment.getCommentId(),
                comment.getContent(),
                comment.getCreatedDatetime(),
                comment.getBoard().getBoardId(),
                comment.getMember().getEmail()
        );
    }
}
