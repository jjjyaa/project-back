package com.example.hjtest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "board_file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 원본 파일명 (예: cat.jpg)
    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    // 저장된 경로 (예: images/20250407/987654321.jpg)
    @Column(name = "stored_file_path", nullable = false)
    private String storedFilePath;

    // 파일 크기
    @Column(name = "file_size")
    private Long fileSize;

    // 파일 등록자 (관리자 또는 사용자 이메일)
    @Column(name = "creator_id")
    private String creatorId;

    // 게시글 연관관계 (N:1)
    @JsonBackReference("fileRef")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

}
