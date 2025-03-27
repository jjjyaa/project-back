package com.example.hjtest.controller;

import com.example.hjtest.Dto.BoardDto;
import com.example.hjtest.entity.Board;
import com.example.hjtest.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public List<Board> selectBoard(){
        return boardService.selectBoardList();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Board> detailBoard(@PathVariable("id") Long id){
        Board board = boardService.selectBoardDetail(id);
        if(board ==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(board);
    }
    @PostMapping("/")
    public ResponseEntity<Board> insertBoard(@RequestBody BoardDto boardDto,
                                             @RequestHeader("email") String email){
        Board board = boardService.insertBoard(boardDto,email);
        if (board==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(board);
    }
    @PatchMapping("/{id}/update")
    public ResponseEntity<Board> updateBoard(@PathVariable("id") Long id,
                                             @RequestBody BoardDto boardDto){
        Board board = boardService.updateBoard(id,boardDto);
        if (board==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(board);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteBoarad(@PathVariable("id") Long id){
        boolean isDeleted = boardService.deleteBorad(id);
        if(isDeleted){
            return ResponseEntity.ok("게시글이 삭제되었습니다.");
        }else {
            // 게시글이 존재하지 않으면 404 NOT FOUND 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("게시글이 존재하지 않습니다.");
        }
    }
}
