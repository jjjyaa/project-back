package com.example.hjtest.service;

import com.example.hjtest.entity.Board;
import com.example.hjtest.entity.BoardLike;
import com.example.hjtest.entity.Member;
import com.example.hjtest.repository.BoardLikeRepository;
import com.example.hjtest.repository.BoardRepository;
import com.example.hjtest.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardLikeServiceImpl implements BoardLikeService {

    private final BoardLikeRepository likeRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    public boolean toggleLike(int boardId, String email) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글 없음"));
        Member member = memberRepository.findById(email)
                .orElseThrow(() -> new EntityNotFoundException("회원 없음"));

        Optional<BoardLike> existing = likeRepository.findByBoardAndMember(board, member);
        if (existing.isPresent()) {
            likeRepository.delete(existing.get());
            return false;
        } else {
            BoardLike like = new BoardLike();
            like.setBoard(board);
            like.setMember(member);
            likeRepository.save(like);
            return true;
        }
    }

    @Override
    public int getLikeCount(int boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글 없음"));
        return likeRepository.countByBoard(board);
    }

    @Override
    public boolean hasUserLiked(int boardId, String email) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글 없음"));
        Member member = memberRepository.findById(email)
                .orElseThrow(() -> new EntityNotFoundException("회원 없음"));
        return likeRepository.findByBoardAndMember(board, member).isPresent();
    }
}
