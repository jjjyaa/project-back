package com.example.hjtest.Dto.comment;

import com.example.hjtest.entity.Board;
import com.example.hjtest.entity.Member;
import com.example.hjtest.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequestDto {
    private String content;
    private int boardId;
    private String email;

    public Comment toEntity(Board board, Member member) {
        return new Comment(0, this.content, null, board, member);
    }
}
