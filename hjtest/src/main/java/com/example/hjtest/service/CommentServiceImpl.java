package com.example.hjtest.service;

import com.example.hjtest.Dto.CommentDto;
import com.example.hjtest.entity.Board;
import com.example.hjtest.entity.Comment;
import com.example.hjtest.entity.Member;
import com.example.hjtest.repository.BoardRepository;
import com.example.hjtest.repository.CommentRepository;
import com.example.hjtest.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 댓글을 찾을 수 없습니다: " + commentId));
    }

    // 댓글 생성
    @Override
    public Comment createComment(CommentDto commentDto) {
        Board board = boardRepository.findById(commentDto.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        Member member = memberRepository.findById(commentDto.getMemberEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // DTO를 엔티티로 변환 후 저장
        Comment comment = commentDto.toEntity(board, member);
        return commentRepository.save(comment);
    }

}
