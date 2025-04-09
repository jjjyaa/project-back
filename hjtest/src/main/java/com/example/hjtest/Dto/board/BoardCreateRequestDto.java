package com.example.hjtest.Dto.board;

import com.example.hjtest.entity.Board;
import com.example.hjtest.entity.Member;
import lombok.Data;

@Data
public class BoardCreateRequestDto {
    private String title;
    private String contents;
    private String email; // 작성자 이메일

    public Board toEntity(Member member) {
        Board board = new Board();
        board.setTitle(this.title);
        board.setContents(this.contents);
        board.setMember(member); // 연관관계 주입
        return board;
    }
}
