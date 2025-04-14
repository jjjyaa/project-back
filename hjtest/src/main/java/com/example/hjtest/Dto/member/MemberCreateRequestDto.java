package com.example.hjtest.Dto.member;

import com.example.hjtest.entity.Member;
import lombok.Data;

@Data
public class MemberCreateRequestDto {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .address(address)
                .build();
    }
}
