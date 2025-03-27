package com.example.hjtest.service;

import com.example.hjtest.Dto.BoardDto;
import com.example.hjtest.Exception.BoardNotFoundException;
import com.example.hjtest.entity.Board;
import com.example.hjtest.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public List<Board> selectBoardList() {
        return boardRepository.findAll();
    }

    @Override
    public Board selectBoardDetail(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("Board not found"));
    }

    @Override
    public Board insertBoard(BoardDto boardDto) {
        Board board = boardDto.toEntity();
        return boardRepository.save(board);
    }

    @Override
    public Board updateBoard(Long id,BoardDto boardDto) {
        Board findBoard = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("Board not found"));

        Board boardCheck = boardDto.toEntity();
        findBoard.patchCheck(boardCheck);

        return boardRepository.save(findBoard);
    }

    @Override
    public boolean deleteBorad(Long id) {
        Board findBoard = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("Board not found"));
        boardRepository.delete(findBoard);
        return true;
    }
}
