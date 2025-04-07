package com.example.hjtest.common;

import com.example.hjtest.entity.BoardFileEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// 게시글 파일 업로드 유틸 클래스
@Component
public class FileUtils {

    /**
     * Multipart 요청에서 이미지 파일들을 저장하고, 그 정보를 BoardFileEntity 리스트로 반환
     * @param multipartHttpServletRequest 클라이언트에서 전송한 multipart/form-data 요청
     * @return 저장된 파일 정보를 담은 리스트
     * @throws Exception 파일 저장 실패 시 예외 발생
     */
    public List<BoardFileEntity> parseFileInfo(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        List<BoardFileEntity> fileList = new ArrayList<>();

        // 1. 업로드 경로 생성 (예: images/20250407)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String folderName = ZonedDateTime.now().format(formatter);
        String basePath = System.getProperty("user.dir") + "/src/main/resources/static/images/";
        String uploadPath = basePath + folderName;
        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();

        // 2. 클라이언트에서 보낸 파일 목록 반복
        Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();

        while (fileNames.hasNext()) {
            List<MultipartFile> fileGroup = multipartHttpServletRequest.getFiles(fileNames.next());

            for (MultipartFile multipartFile : fileGroup) {
                if (multipartFile.isEmpty()) continue;

                String contentType = multipartFile.getContentType();
                if (ObjectUtils.isEmpty(contentType)) continue;

                // 3. 이미지 확장자 확인
                String fileExtension;
                if (contentType.contains("image/jpeg")) fileExtension = ".jpg";
                else if (contentType.contains("image/png")) fileExtension = ".png";
                else if (contentType.contains("image/gif")) fileExtension = ".gif";
                else continue; // 이미지가 아닌 경우 무시

                // 4. 저장할 파일명 생성 (중복 방지를 위해 nanoTime 사용)
                String newFileName = System.nanoTime() + fileExtension;
                String fullPath = uploadPath + "/" + newFileName;

                // 5. 실제 파일 저장
                File saveFile = new File(fullPath);
                multipartFile.transferTo(saveFile);

                // 6. 저장 정보 엔티티에 담기
                BoardFileEntity fileEntity = new BoardFileEntity();
                fileEntity.setOriginalFileName(multipartFile.getOriginalFilename());
                fileEntity.setStoredFilePath(fullPath);
                fileEntity.setFileSize(multipartFile.getSize());
                fileEntity.setCreatorId("admin"); // 필요 시 로그인 사용자로 대체

                fileList.add(fileEntity);
            }
        }

        return fileList;
    }
}

