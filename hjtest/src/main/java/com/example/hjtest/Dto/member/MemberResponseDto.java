package com.example.hjtest.Dto.member;

import com.example.hjtest.entity.Member;
import lombok.Data;

@Data
public class MemberResponseDto {
    private String email;
    private String name;
    private String phone;
    private String address;

    public MemberResponseDto(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
        this.phone = member.getPhone();
        this.address = member.getAddress();
    }
}
