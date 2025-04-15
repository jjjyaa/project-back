package com.example.hjtest.service;

import com.example.hjtest.Dto.board.BoardLikeStatusReponseDto;

public interface BoardLikeService {
    boolean toggleLike(int boardId, String email); // 좋아요 토글

    BoardLikeStatusReponseDto getLikeStatus(int boardId, String email);
}
