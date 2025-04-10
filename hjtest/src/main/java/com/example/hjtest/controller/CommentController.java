package com.example.hjtest.controller;


import com.example.hjtest.Dto.comment.CommentCreateRequestDto;
import com.example.hjtest.Dto.comment.CommentListResponseDto;
import com.example.hjtest.Dto.comment.CommentResponseDto;
import com.example.hjtest.Dto.comment.CommentUpdateRequestDto;
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
    public ResponseEntity<List<CommentListResponseDto>> getCommentByBoardId(@PathVariable("boardId") int boardId) {
        List<CommentListResponseDto> responseDto = commentService.getCommentByBoardId(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentCreateRequestDto requestDtoDto) {
        try{
            CommentResponseDto ResponseDto = commentService.createComment(requestDtoDto);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 해야합니다.");
        }
    }
    @PatchMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable("commentId") int commentId,
                                           @RequestBody
                                           CommentUpdateRequestDto requestDto){
        try{
            CommentResponseDto ResponseDto = commentService.updateComment(commentId, requestDto);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글이 없습니다");
        }
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") int commentId){
        try{
            boolean isDeleted = commentService.deleteComment(commentId);
            return ResponseEntity.ok("댓글이 삭제되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("댓글이 없습니다");
        }
    }
}