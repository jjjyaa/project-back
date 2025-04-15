package com.example.hjtest.controller;

import com.example.hjtest.Dto.board.*;
import com.example.hjtest.common.FileUtils;
import com.example.hjtest.service.BoardService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
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
    public ResponseEntity<?> updateBoard(@PathVariable("id") int id,
                                                        @RequestPart("dto") BoardUpdateRequestDto dto, // "dto" 이름으로 받기
                                                        @RequestPart(value = "file", required = false) List<MultipartFile> files) { // "file" 이름으로 받기
        try {
            // 서비스 메서드 호출하여 수정된 BoardResponseDto 받기
            BoardResponseDto updatedBoard = boardService.updateBoard(id, dto, files);

            // 성공적으로 수정되었으면 OK 상태와 함께 반환
            return ResponseEntity.status(HttpStatus.OK).body(updatedBoard);

        } catch (EntityNotFoundException e) {
            //게시글이 없으면
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글이 없습니다.");
        } catch (IllegalArgumentException e) {
            // 이메일 불일치 예외 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일이 일치하지 않습니다.");
        } catch (Exception e) {
            // 그 외 모든 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
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
    public ResponseEntity<BoardPageResponseDto> searchBoards(@RequestParam("searchTerm") String searchTerm,
                                                   @RequestParam("searchType") String searchType,
                                                   @RequestParam("page") int page,
                                                   @RequestParam("size") int size) {

        BoardPageResponseDto dto = boardService.getPagedBoards(searchTerm, searchType,page,size);
        return ResponseEntity.ok(dto);
    }
}