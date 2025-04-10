package com.example.hjtest.Dto.comment;

import com.example.hjtest.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateRequestDto {
    private String content;
    private String email;

    public Comment toEntity(Comment comment) {
        comment.setContent(this.content);
        return comment;
    }
}
