package com.example.hjtest.common;

import com.example.hjtest.Dto.BoardDto;
import com.example.hjtest.entity.BoardFileEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class FileUtils {

    /**
     * 파일 리스트를 받아서 각 파일을 저장하고 BoardFileEntity 리스트를 반환
     * @param files 클라이언트에서 전송한 파일들
     * @return 저장된 파일 정보를 담은 리스트
     * @throws Exception 파일 저장 실패 시 예외 발생
     */
    public List<BoardFileEntity> parseFileInfo(List<MultipartFile> files, BoardDto boardDto) throws Exception {
        List<BoardFileEntity> fileList = new ArrayList<>();

        // 1. 업로드 경로 생성 (예: images/20250408)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String folderName = ZonedDateTime.now().format(formatter);

        // 현재 프로젝트 디렉토리와 upload 폴더를 합친 경로를 설정
        String basePath = System.getProperty("user.dir");
        String parentPath = Paths.get(basePath, "../upload").toString();  // 상위 폴더 (../)로 이동
        String uploadPath = Paths.get(parentPath, folderName).toString(); // folderName을 포함하여 전체 경로 설정

        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();  // 디렉토리 생성
        }

        // 2. 클라이언트에서 보낸 파일 목록 반복
        for (MultipartFile multipartFile : files) {
            if (multipartFile.isEmpty()) continue;

            String contentType = multipartFile.getContentType();
            if (ObjectUtils.isEmpty(contentType)) continue;

            // 3. 이미지 확장자 확인
            String fileExtension = null;
            if (contentType.contains("image/jpeg")) {
                fileExtension = ".jpg";
            } else if (contentType.contains("image/png")) {
                fileExtension = ".png";
            } else if (contentType.contains("image/gif")) {
                fileExtension = ".gif";
            } else {
                continue; // 이미지가 아닌 경우 무시
            }

            String newFileName = System.nanoTime() + fileExtension;
            String fullPath = uploadPath + "/" + newFileName;

            // 5. 실제 파일 저장
            File saveFile = new File(fullPath);
            multipartFile.transferTo(saveFile);

            // 6. 저장 정보 엔티티에 담기
            BoardFileEntity fileEntity = new BoardFileEntity();
            fileEntity.setOriginalFileName(multipartFile.getOriginalFilename());
            fileEntity.setStoredFilePath(folderName + "/" + newFileName); // 상대 경로만 저장
            fileEntity.setFileSize(multipartFile.getSize());
            fileEntity.setCreatorId(boardDto.getEmail());

            fileList.add(fileEntity);
        }

        return fileList;
    }
}