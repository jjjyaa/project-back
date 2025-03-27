package com.example.hjtest.service;

import com.example.hjtest.Dto.BoardDto;
import com.example.hjtest.Dto.CommentDto;
import com.example.hjtest.Exception.BoardNotFoundException;
import com.example.hjtest.entity.Board;
import com.example.hjtest.entity.Comment;
import com.example.hjtest.entity.Member;
import com.example.hjtest.repository.BoardRepository;
import com.example.hjtest.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<Board> selectBoardList() {
        return boardRepository.findAll();
    }

    @Override
    public Board selectBoardDetail(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("Board not found"));
    }

    @Override
    public Board insertBoard(BoardDto boardDto, String email) {
        // 로그인한 사용자의 Member 정보 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // BoardDto를 Board 엔티티로 변환
        Board board = boardDto.toEntity();

        // 댓글 리스트를 엔티티로 변환
        List<Comment> commentEntities = convertComments(boardDto.getComments(), board);

        // Board에 Member 설정
        board.setMember(member);

        // 댓글 리스트를 Board 엔티티에 설정
        board.setComments(commentEntities);

        // 게시글 저장
        return boardRepository.save(board);
    }

    @Override
    public Board updateBoard(Long boardId, BoardDto boardDto) {
        // 기존 Board 조회
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        // BoardDto -> Board 엔티티로 변환
        Board boardCheck = boardDto.toEntity();
        findBoard.patchCheck(boardCheck); // 업데이트 체크

        // 댓글 리스트를 엔티티로 변환
        List<Comment> commentEntities = convertComments(boardDto.getComments(), findBoard);

        // 댓글 리스트를 Board 엔티티에 설정
        findBoard.setComments(commentEntities);

        return boardRepository.save(findBoard);
    }

    @Override
    public boolean deleteBorad(Long boardId) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("Board not found"));
        boardRepository.delete(findBoard);
        return true;
    }

    //조회수 1증가
    @Override
    public void incrementHitCount(Long boardId) {
        boardRepository.updateHitCount(boardId);
    }
    // 댓글을 엔티티로 변환하는 메서드
    private List<Comment> convertComments(List<CommentDto> commentDtos, Board board) {
        return commentDtos.stream()
                .map(commentDto -> {
                    // 해당 Member를 DB에서 조회
                    Member memberEntity = memberRepository.findById(commentDto.getMemberId())
                            .orElseThrow(() -> new IllegalArgumentException("Member not found"));

                    // 변환된 엔티티를 반환
                    return commentDto.toEntity(board, memberEntity); // Board와 Member를 전달
                })
                .collect(Collectors.toList());
    }
}
