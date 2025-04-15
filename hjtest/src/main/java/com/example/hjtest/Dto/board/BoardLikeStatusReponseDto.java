package com.example.hjtest.Dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardLikeStatusReponseDto {
    private int likeCount;
    private boolean liked;
}
