package com.example.hjtest.controller;

import com.example.hjtest.Dto.board.BoardCreateRequestDto;
import com.example.hjtest.Dto.board.BoardListResponseDto;
import com.example.hjtest.Dto.board.BoardResponseDto;
import com.example.hjtest.Dto.board.BoardUpdateRequestDto;
import com.example.hjtest.common.FileUtils;
import com.example.hjtest.service.BoardService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/boards")
public class BoardController {

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public ResponseEntity<List<BoardListResponseDto>> selectBoard() {
        List<BoardListResponseDto> boards = boardService.selectBoardList();
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BoardListResponseDto> detailBoard(@PathVariable("id") int id) {
        BoardListResponseDto boardResponseDto = boardService.selectBoardDetail(id);
        if (boardResponseDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(boardResponseDto);
    }
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BoardResponseDto> insertBoard(@RequestPart("dto") BoardCreateRequestDto dto,
                                                        @RequestPart(value = "file", required = false) List<MultipartFile> files) {
        BoardResponseDto response = boardService.insertBoard(dto, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping(value = "/{id}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable("id") int id,
                                                        @RequestPart("dto") BoardUpdateRequestDto dto, // "dto" 이름으로 받기
                                                        @RequestPart(value = "file", required = false) List<MultipartFile> files) { // "file" 이름으로 받기

        // 서비스 메서드 호출하여 수정된 BoardResponseDto 받기
        BoardResponseDto updatedBoard = boardService.updateBoard(id, dto, files);

        // 수정된 결과가 없으면 BAD_REQUEST 상태 반환
        if (updatedBoard == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 성공적으로 수정되었으면 OK 상태와 함께 반환
        return ResponseEntity.status(HttpStatus.OK).body(updatedBoard);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteBoarad(@PathVariable("id") int id){
        try {
            boolean isDeleted = boardService.deleteBoard(id);
            return ResponseEntity.ok("게시글이 삭제되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글이 없습니다");
        }
    }

    @GetMapping("/search")
    public List<BoardListResponseDto> searchBoards(@RequestParam("searchTerm") String searchTerm,
                                                   @RequestParam("searchType") String searchType) {
        return boardService.searchBoards(searchTerm, searchType);
    }
}