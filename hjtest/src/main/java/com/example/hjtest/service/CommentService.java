package com.example.hjtest.service;


import com.example.hjtest.Dto.comment.CommentCreateRequestDto;
import com.example.hjtest.Dto.comment.CommentListResponseDto;
import com.example.hjtest.Dto.comment.CommentResponseDto;
import com.example.hjtest.Dto.comment.CommentUpdateRequestDto;


import java.util.List;

public interface CommentService {
    public CommentResponseDto createComment(CommentCreateRequestDto Dto);

    public List<CommentListResponseDto> getCommentByBoardId(int boardId);

    public CommentResponseDto updateComment(int commentId, CommentUpdateRequestDto Dto);

    public boolean deleteComment(int commentId);
}
