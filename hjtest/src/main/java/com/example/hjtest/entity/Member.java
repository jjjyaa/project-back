package com.example.hjtest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entity 는 DB 테이블과 매핑
// DB에 실제로 저장될 사용자 정보
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 값
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private String address;

}


