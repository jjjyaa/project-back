//package com.example.hjtest.Dto;
//
//import com.example.hjtest.entity.Board;
//import com.example.hjtest.entity.Comment;
//import com.example.hjtest.entity.Member;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Data;
//
//import java.util.ArrayList;
//import java.util.List;
//
//// React에서 입력한 데이터를 서버로 전달받기 위한 데이터 전달 객체
//
//// 회원가입용
//@Data
//public class MemberDto {
//    private String email;
//    private String password;
//    private String name;
//    private String phone;
//    private String address;
//
//    @JsonIgnore
//    private List<Board> boards = new ArrayList<>();
//    @JsonIgnore
//    private List<Comment> comments = new ArrayList<>();
//
//    public Member toEntity(){
//
//        return new Member(email, password, name, phone, address,boards,comments,null);
//    }
//}
