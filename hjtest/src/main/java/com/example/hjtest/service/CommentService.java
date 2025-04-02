package com.example.hjtest.service;

import com.example.hjtest.Dto.CommentDto;
import com.example.hjtest.entity.Comment;

import java.util.List;

public interface CommentService {
    public Comment createComment(CommentDto commentDto);

    public List<Comment> getCommentByBoardId(int boardId);
}
