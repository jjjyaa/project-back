package com.example.hjtest.controller;

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

    @GetMapping("/{boardId}/count")
    public ResponseEntity<Integer> getLikeCount(@PathVariable int boardId) {
        int count = boardLikeService.getLikeCount(boardId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{boardId}/status")
    public ResponseEntity<Boolean> hasUserLiked(@PathVariable int boardId,
                                                @RequestParam String email) {
        boolean liked = boardLikeService.hasUserLiked(boardId, email);
        return ResponseEntity.ok(liked);
    }
}
