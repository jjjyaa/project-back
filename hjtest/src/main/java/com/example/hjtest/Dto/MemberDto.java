package com.example.hjtest.Dto;

import com.example.hjtest.entity.Member;
import lombok.Data;

// React에서 입력한 데이터를 서버로 전달받기 위한 데이터 전달 객체

// 회원가입용
@Data
public class MemberDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;

    public Member toEntity(){

        return new Member(id,email, password, name, phone, address);
    }
}
