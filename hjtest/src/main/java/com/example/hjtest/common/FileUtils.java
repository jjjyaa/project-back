package com.example.hjtest.common;

import com.example.hjtest.entity.BoardFileEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileUtils {

    /**
     * 파일 리스트를 받아서 저장 후 BoardFileEntity 리스트 반환
     * @param files Multipart 파일 리스트
     * @param creatorEmail 작성자 이메일 (BoardDto 제거)
     * @return 저장된 파일 엔티티 리스트
     * @throws Exception
     */
    public List<BoardFileEntity> parseFileInfo(List<MultipartFile> files, String creatorEmail) throws Exception {
        List<BoardFileEntity> fileList = new ArrayList<>();
        if (files == null || files.isEmpty()) return fileList;
        // 날짜 폴더 설정 (예: 20250408)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String folderName = ZonedDateTime.now().format(formatter);

        // 경로 설정: ../upload/yyyyMMdd
        String basePath = System.getProperty("user.dir");
        String parentPath = Paths.get(basePath, "../upload").toString();
        String uploadPath = Paths.get(parentPath, folderName).toString();

        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs(); // 폴더 없으면 생성
        }

        // 파일 하나씩 저장
        for (MultipartFile multipartFile : files) {
            if (multipartFile.isEmpty()) continue;

            String contentType = multipartFile.getContentType();
            if (ObjectUtils.isEmpty(contentType)) continue;

            // 확장자 확인
            String fileExtension = null;
            if (contentType.contains("image/jpeg")) fileExtension = ".jpg";
            else if (contentType.contains("image/png")) fileExtension = ".png";
            else if (contentType.contains("image/gif")) fileExtension = ".gif";
            else continue;

            String newFileName = System.nanoTime() + fileExtension;
            String fullPath = uploadPath + "/" + newFileName;

            // 실제 저장
            File saveFile = new File(fullPath);
            multipartFile.transferTo(saveFile);

            // 엔티티 생성
            BoardFileEntity fileEntity = new BoardFileEntity();
            fileEntity.setOriginalFileName(multipartFile.getOriginalFilename());
            fileEntity.setStoredFilePath(folderName + "/" + newFileName);
            fileEntity.setFileSize(multipartFile.getSize());
            fileEntity.setCreatorId(creatorEmail); // 여기 변경

            fileList.add(fileEntity);
        }

        return fileList;
    }

    public void deleteFile(String filePath) throws Exception {
        // 'upload' 디렉토리 경로를 기준으로 파일 경로를 결합
        String basePath = System.getProperty("user.dir");  // 현재 프로젝트 디렉토리 경로
        String uploadPath = Paths.get(basePath, "..", "upload").toString();  // ../upload 디렉토리
        String fullPath = Paths.get(uploadPath, filePath).toString();  // 상대 경로로부터 전체 경로 계산

        File file = new File(fullPath);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("파일 삭제 성공: " + fullPath);
            } else {
                throw new Exception("파일 삭제에 실패했습니다: " + fullPath);
            }
        } else {
            throw new Exception("파일이 존재하지 않습니다: " + fullPath);  // 파일이 존재하지 않으면 예외 발생
        }
    }
}
