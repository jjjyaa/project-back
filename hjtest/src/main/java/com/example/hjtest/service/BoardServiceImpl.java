package com.example.hjtest.service;

import com.example.hjtest.Dto.BoardCreateRequestDto;
import com.example.hjtest.Dto.BoardDto;
import com.example.hjtest.Dto.BoardResponseDto;
import com.example.hjtest.Dto.CommentDto;
import com.example.hjtest.common.FileUtils;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FileUtils fileUtils;

    @Override
    public List<Board> selectBoardList() {
        return boardRepository.findAllByOrderByBoardIdDesc();
    }

    @Override
    public Board selectBoardDetail(int boardId) {

        //조회수 1 증가
        boardRepository.updateHitCount(boardId);
        //조회수1 증가 한거 데이터베이스에 반영
        boardRepository.flush();

        return boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
    }

    @Override
    public BoardResponseDto insertBoard(BoardCreateRequestDto dto, List<MultipartFile> files) {
        // 1. 이메일로 작성자(Member) 조회
        Member member = memberRepository.findById(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        // 2. DTO → Board 엔티티 변환
        Board board = dto.toEntity(member);

        // 3. 파일이 있다면 업로드 처리
        if (files != null && !files.isEmpty()) {
            try {
                List<BoardFileEntity> fileEntities = fileUtils.parseFileInfo(files, dto.getEmail());

                for (BoardFileEntity file : fileEntities) {
                    file.setBoard(board);
                    board.getFileList().add(file);
                }
            } catch (Exception e) {
                log.error("파일 업로드 중 오류 발생", e);
                throw new RuntimeException("파일 저장 실패", e);
            }
        }

        // 4. 저장
        Board saved = boardRepository.save(board);

        // 5. 응답 DTO로 변환해서 리턴
        return BoardResponseDto.fromEntity(saved);
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