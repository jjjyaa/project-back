package com.example.hjtest.Dto.board;
import com.example.hjtest.entity.Board;
import lombok.Data;

@Data
public class BoardUpdateRequestDto {
    private String title;
    private String contents;
    private String email;
    //메서드 방식
    public Board toEntity(Board board) {
        board.setTitle(this.title != null ? this.title : board.getTitle());
        board.setContents(this.contents != null ? this.contents : board.getContents());
        return board;
    }
}