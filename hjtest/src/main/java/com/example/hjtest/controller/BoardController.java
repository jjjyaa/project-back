package com.example.hjtest.controller;

import com.example.hjtest.Dto.BoardDto;
import com.example.hjtest.common.FileUtils;
import com.example.hjtest.entity.Board;
import com.example.hjtest.entity.BoardFileEntity;
import com.example.hjtest.service.BoardService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(value = "/", consumes = "multipart/form-data")
    public ResponseEntity<Board> insertBoard(@ModelAttribute BoardDto boardDto,
                                             @RequestParam("file") List<MultipartFile> files) {
        try {
            // 파일 저장 처리
            List<BoardFileEntity> fileList = fileUtils.parseFileInfo(files,boardDto);
            boardDto.setFileList(fileList);

            // 게시글 + 파일 저장
            Board board = boardService.insertBoard(boardDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(board);
        } catch (Exception e) {
            log.error("게시글 작성 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PatchMapping("/{id}/update")
    public ResponseEntity<Board> updateBoard(@PathVariable("id") int id,
                                             @RequestBody BoardDto boardDto){
        Board board = boardService.updateBoard(id,boardDto);
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