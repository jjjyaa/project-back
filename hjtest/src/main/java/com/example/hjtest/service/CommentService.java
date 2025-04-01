package com.example.hjtest.service;

import com.example.hjtest.Dto.CommentDto;
import com.example.hjtest.entity.Comment;

public interface CommentService {
    public Comment createComment(CommentDto commentDto);

    public Comment getCommentById(Long commentId);
}
