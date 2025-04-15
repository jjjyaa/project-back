package com.example.hjtest.controller;

import com.example.hjtest.Dto.board.BoardLikeStatusReponseDto;
import com.example.hjtest.service.BoardLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    @PostMapping("/{boardId}")
    public ResponseEntity<String> toggleLike(@PathVariable int boardId,
                                             @RequestParam String email) {
        boolean liked = boardLikeService.toggleLike(boardId, email);
        return ResponseEntity.ok(liked ? "좋아요 등록" : "좋아요 취소");
    }

    @GetMapping("/{boardId}/like-status")
    public ResponseEntity<BoardLikeStatusReponseDto> getLikeStatus(
            @PathVariable int boardId,
            @RequestParam String email) {

        BoardLikeStatusReponseDto response = boardLikeService.getLikeStatus(boardId, email);

        return ResponseEntity.ok(response);
    }
}
