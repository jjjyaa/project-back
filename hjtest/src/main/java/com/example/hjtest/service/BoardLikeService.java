package com.example.hjtest.service;

public interface BoardLikeService {
    boolean toggleLike(int boardId, String email); // 좋아요 토글
    int getLikeCount(int boardId);                // 좋아요 수 조회
    boolean hasUserLiked(int boardId, String email); // 사용자 좋아요 여부
}
