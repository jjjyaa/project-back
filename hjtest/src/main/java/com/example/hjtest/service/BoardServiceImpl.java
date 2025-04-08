package com.example.hjtest.service;

import com.example.hjtest.Dto.BoardDto;
import com.example.hjtest.Dto.CommentDto;
import com.example.hjtest.entity.Board;
import com.example.hjtest.entity.BoardFileEntity;
import com.example.hjtest.entity.Comment;
import com.example.hjtest.entity.Member;
import com.example.hjtest.repository.BoardRepository;
import com.example.hjtest.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
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
        return boardRepository.findAllByOrderByCreatedDatetimeDesc();
    }

    @Override
    public Board selectBoardDetail(int boardId) {

        //조회수 1 증가
        boardRepository.updateHitCount(boardId);
        boardRepository.flush();

        return boardRepository.findDetailById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
    }

    @Override
    public Board insertBoard(BoardDto boardDto, List<BoardFileEntity> fileList) {
        // 1. 로그인한 사용자의 Member 정보 조회
        Member member = memberRepository.findById(boardDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 2. BoardDto를 Board 엔티티로 변환
        Board board = boardDto.toEntity();

        // 3. 댓글 리스트를 엔티티로 변환
        List<Comment> commentEntities = convertComments(boardDto.getComments(), board);

        // 4. Board에 Member, Comments 설정
        board.setMember(member);
        board.setComments(commentEntities);

        // 5. 파일 리스트와 Board 연결
        if (fileList != null && !fileList.isEmpty()) {
            for (BoardFileEntity file : fileList) {
                file.setBoard(board);                   // 외래키 설정
                board.getFileList().add(file);          // 연관 리스트 추가
            }
        }

        // 6. 저장
        return boardRepository.save(board);
    }

    @Override
    public Board updateBoard(int boardId, BoardDto boardDto) {
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
    public boolean deleteBorad(int boardId) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        boardRepository.delete(findBoard);
        return true;
    }


    // 댓글을 엔티티로 변환하는 메서드
    private List<Comment> convertComments(List<CommentDto> commentDtos, Board board) {
        return commentDtos.stream()
                .map(commentDto -> {
                    // 해당 Member를 DB에서 조회
                    Member memberEntity = memberRepository.findById(commentDto.getEmail())
                            .orElseThrow(() -> new IllegalArgumentException("Member not found"));

                    // 변환된 엔티티를 반환
                    return commentDto.toEntity(board, memberEntity); // Board와 Member를 전달
                })
                .collect(Collectors.toList());
    }
}
