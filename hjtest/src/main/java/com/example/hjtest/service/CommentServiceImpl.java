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

import java.util.List;
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
    public List<Comment> getCommentByBoardId(int boardId) {
        return commentRepository.findAllByBoardBoardId(boardId);
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

    // 댓글 수정
    @Override
    public Comment updateComment(int commentId, CommentDto commentDto, String email) {
        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        // 작성자 확인
        if (!comment.getMember().getEmail().equals(email)) {
            throw new IllegalArgumentException("You are not the author of this comment.");
        }

        // 댓글 내용 수정
        comment.setContent(commentDto.getContent());

        // 댓글 수정된 내용 저장
        return commentRepository.save(comment);
    }

    // 댓글 삭제
    @Override
    public boolean deleteComment(int commentId, String email) {
        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        // 댓글 작성자가 요청한 회원인지 확인
        if (!comment.getMember().getEmail().equals(email)) {
            throw new IllegalArgumentException("You are not the author of this comment.");
        }

        // 댓글 삭제
        commentRepository.delete(comment);
        return true;
    }
}
