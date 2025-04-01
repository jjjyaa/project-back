package com.example.hjtest.controller;

import com.example.hjtest.Dto.CommentDto;
import com.example.hjtest.entity.Comment;
import com.example.hjtest.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }
    @PostMapping
    public Comment createComment(@RequestBody CommentDto commentDto) {
        return commentService.createComment(commentDto);
    }
}
