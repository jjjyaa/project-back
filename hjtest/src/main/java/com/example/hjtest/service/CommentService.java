package com.example.hjtest.service;

import com.example.hjtest.Dto.CommentDto;
import com.example.hjtest.entity.Comment;

import java.util.List;

public interface CommentService {
    public Comment createComment(CommentDto commentDto);

    public List<Comment> getCommentByBoardId(int boardId);

    public Comment updateComment(int commentId, CommentDto commentDto, String email);

    public boolean deleteComment(int commentId, String email);
}
