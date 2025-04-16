package com.example.hjtest.Dto.member;

import lombok.Data;
import com.example.hjtest.entity.Member;

@Data
public class MemberUpdateRequestDto {
    private String password;
    private String name;
    private String phone;
    private String address;

    public Member toEntity() {
        Member member = new Member();
        member.setPassword(this.password);
        member.setName(this.name);
        member.setPhone(this.phone);
        member.setAddress(this.address);
        return member;
    }
}
