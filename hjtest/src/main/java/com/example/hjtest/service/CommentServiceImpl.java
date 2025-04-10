package com.example.hjtest.service;

import com.example.hjtest.Dto.comment.CommentCreateRequestDto;
import com.example.hjtest.Dto.comment.CommentListResponseDto;
import com.example.hjtest.Dto.comment.CommentResponseDto;
import com.example.hjtest.Dto.comment.CommentUpdateRequestDto;
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
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<CommentListResponseDto> getCommentByBoardId(int boardId) {
        List<Comment> comments = commentRepository.findAllByBoardBoardId(boardId);
        return comments.stream()
                .map(comment -> new CommentListResponseDto(comment))
                .collect(Collectors.toList());
    }
    // 댓글 생성
    @Override
    public CommentResponseDto createComment(CommentCreateRequestDto Dto) {
        Board board = boardRepository.findById(Dto.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        Member member = memberRepository.findById(Dto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // DTO를 엔티티로 변환 후 저장
        Comment comment = Dto.toEntity(board, member);
        comment = commentRepository.save(comment);
        return CommentResponseDto.fromEntity(comment);
    }

    // 댓글 수정
    @Override
    public CommentResponseDto updateComment(int commentId, CommentUpdateRequestDto Dto) {
        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        // 작성자 확인
        if (!comment.getMember().getEmail().equals(Dto.getEmail())) {
            throw new IllegalArgumentException("You are not the author of this comment.");
        }
        // 댓글 내용 수정
        Dto.toEntity(comment);
        // 댓글 수정된 내용 저장
        comment = commentRepository.save(comment);

        return  CommentResponseDto.fromEntity(comment);
    }

    // 댓글 삭제
    @Override
    public boolean deleteComment(int commentId) {
        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        // 댓글 삭제
        commentRepository.delete(comment);
        return true;
    }
}
