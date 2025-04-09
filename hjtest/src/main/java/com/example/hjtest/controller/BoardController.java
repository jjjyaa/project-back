package com.example.hjtest.controller;

import com.example.hjtest.Dto.BoardCreateRequestDto;
import com.example.hjtest.Dto.BoardDto;
import com.example.hjtest.Dto.BoardResponseDto;
import com.example.hjtest.common.FileUtils;
import com.example.hjtest.entity.Board;
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
    public List<Board> selectBoard(){
        return boardService.selectBoardList();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Board> detailBoard(@PathVariable("id") int id){
        Board board = boardService.selectBoardDetail(id);
        if(board ==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(board);
    }
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BoardResponseDto> insertBoard(
            @RequestPart("dto") BoardCreateRequestDto dto,
            @RequestPart(value = "file", required = false) List<MultipartFile> files
    ) {
        BoardResponseDto response = boardService.insertBoard(dto, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Board> updateBoard(@PathVariable("id") int id,
                                             @RequestBody BoardDto boardDto){
        Board board = boardService.updateBoard(id,boardDto);
        System.out.println("받은 DTO: " + boardDto);
        if (board==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(board);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteBoarad(@PathVariable("id") int id){
        try {
            boolean isDeleted = boardService.deleteBorad(id);
            return ResponseEntity.ok("게시글이 삭제되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글이 없습니다");
        }
    }
}