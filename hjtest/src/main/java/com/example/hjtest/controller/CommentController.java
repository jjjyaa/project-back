package com.example.hjtest.controller;

import com.example.hjtest.Dto.CommentDto;
import com.example.hjtest.entity.Comment;
import com.example.hjtest.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/comments")
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{boardId}")
    public List<Comment> getComment(@PathVariable("boardId") int boardId) {
        return commentService.getCommentByBoardId(boardId);
    }
    @PostMapping
    public Comment createComment(@RequestBody CommentDto commentDto) {
        return commentService.createComment(commentDto);
    }
}