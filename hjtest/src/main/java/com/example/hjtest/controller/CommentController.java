package com.example.hjtest.controller;

import com.example.hjtest.Dto.CommentDto;
import com.example.hjtest.entity.Comment;
import com.example.hjtest.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/board/{boardId}")
    public List<Comment> getComment(@PathVariable("boardId") int boardId) {
        return commentService.getCommentByBoardId(boardId);
    }
    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto) {
        try{
            Comment comment = commentService.createComment(commentDto);
            return ResponseEntity.status(HttpStatus.OK).body(comment);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 해야합니다.");
        }
    }
    @PatchMapping("/{commentId}/update")
    public ResponseEntity<?> updateComment(@PathVariable("commentId") int commentId,
                                           @RequestBody CommentDto commentDto){
        try{
            Comment updatedComment = commentService.updateComment(commentId, commentDto);
            return ResponseEntity.status(HttpStatus.OK).body(updatedComment);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글이 없습니다");
        }
    }
    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") int commentId,
                                           @RequestBody CommentDto commentDto){
        try{
            boolean isDeleted = commentService.deleteComment(commentId);
            return ResponseEntity.ok("댓글이 삭제되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("댓글이 없습니다");
        }
    }
}