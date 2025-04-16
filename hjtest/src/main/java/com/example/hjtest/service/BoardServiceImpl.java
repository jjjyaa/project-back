package com.example.hjtest.service;

import com.example.hjtest.Dto.board.*;
import com.example.hjtest.common.FileUtils;
import com.example.hjtest.entity.Board;
import com.example.hjtest.entity.BoardFileEntity;
import com.example.hjtest.entity.Member;
import com.example.hjtest.mapper.BoardMapper;
import com.example.hjtest.repository.BoardRepository;
import com.example.hjtest.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private BoardMapper boardMapper;

    @Override
    public BoardListResponseDto  selectBoardDetail(int boardId) {

        //조회수 1 증가
        boardRepository.updateHitCount(boardId);

        // Board 객체 조회
        Board board = boardRepository.findDetailById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));

        // Board 객체를 BoardListResponseDto로 변환하여 반환
        return new BoardListResponseDto(board);
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
                // 파일 처리 로직
                List<BoardFileEntity> fileEntities = fileUtils.parseFileInfo(files, dto.getEmail());

                for (BoardFileEntity file : fileEntities) {
                    file.setBoard(board);  // 파일과 게시글 연결
                    board.getFileList().add(file);  // 게시글에 파일 추가
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
    public BoardResponseDto updateBoard(int boardId, BoardUpdateRequestDto dto, List<MultipartFile> files) {
        // 기존 Board 조회
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));
        // 이메일이 일치하는지 확인
        if (dto.getEmail() == null || !dto.getEmail().equals(findBoard.getMember().getEmail())) {
            throw new IllegalArgumentException("Email does not match the board owner's email");
        }
        // dto -> 기존 Board 엔티티로 변환
        Board updatedBoard = dto.toEntity(findBoard);

        // 파일이 있다면 업로드 처리
        if (files != null && !files.isEmpty()) {
            try {
                // 1. 기존 파일 목록 조회
                List<BoardFileEntity> existingFiles = findBoard.getFileList();

                // 2. 새 파일을 처리할 리스트 생성
                List<BoardFileEntity> newFiles = fileUtils.parseFileInfo(files, dto.getEmail());

                // 3. 기존 파일들과 비교하여 새로운 파일만 처리
                for (BoardFileEntity newFile : newFiles) {
                    boolean isDuplicate = false;

                    // 기존 파일 목록에서 동일한 파일이 있는지 확인
                    for (BoardFileEntity existingFile : existingFiles) {
                        if (existingFile.getOriginalFileName().equals(newFile.getOriginalFileName()) &&
                                existingFile.getFileSize() == newFile.getFileSize()) {
                            // 기존 파일과 동일하면, 새로 추가할 필요 없음
                            isDuplicate = true;
                            break;
                        }
                    }

                    // 동일한 파일이 아니라면, 새로운 파일로 추가
                    if (!isDuplicate) {
                        newFile.setBoard(updatedBoard);  // 새로운 파일과 게시글 연결
                        updatedBoard.getFileList().add(newFile);  // 게시글에 새 파일 추가
                    }
                }

                // 4. 파일이 없다면 기존 파일 삭제
                if (newFiles.isEmpty()) {
                    // 기존 파일 삭제 (파일 시스템에서 삭제하는 로직 추가)
                    for (BoardFileEntity existingFile : existingFiles) {
                        try {
                            // 파일 삭제 로직 (파일 시스템에서 삭제)
                            fileUtils.deleteFile(existingFile.getStoredFilePath());
                        } catch (Exception e) {
                            log.error("파일 삭제 실패", e);
                        }
                    }

                    // 기존 파일 리스트 비우기
                    findBoard.getFileList().clear();  // 기존 파일들 제거
                }
            } catch (Exception e) {
                log.error("파일 업로드 중 오류 발생", e);
                throw new RuntimeException("파일 저장 실패", e);
            }
        }

        // 게시글 저장 (기존 파일을 그대로 두고 새 파일만 추가한 후 업데이트)
        Board savedBoard = boardRepository.save(updatedBoard);

        // BoardResponseDto로 변환하여 반환
        return BoardResponseDto.fromEntity(savedBoard);
    }


    @Override
    public boolean deleteBoard(int boardId) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        boardRepository.delete(findBoard);
        return true;
    }

    // 게시글 목록과 페이징 관련 정보 반환
    @Override
    public BoardPageResponseDto getPagedBoards(String searchTerm, String searchType, int page, int size) {
        // offset 계산
        int offset = (page - 1) * size;

        // 게시글 목록 조회
        List<BoardListResponseDto> boards = boardMapper.searchBoards(searchTerm, searchType, offset, size);

        // 전체 게시글 수 조회
        int totalElements = boardMapper.countBoards(searchTerm, searchType);

        // 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalElements / size);

        // 현재 페이지에 보여지는 게시글 수
        int numberOfElements = boards.size();

        // @AllArgsConstructor를 이용하여 BoardPageResponseDto 생성
        return new BoardPageResponseDto(boards, totalElements, totalPages, size, page, numberOfElements);
    }

}