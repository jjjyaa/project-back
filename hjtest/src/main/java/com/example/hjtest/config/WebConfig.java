package com.example.hjtest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // /api로 시작하는 모든 경로에 대해 CORS 허용
                .allowedOrigins("http://localhost:3000") // 허용할 도메인
                .allowedMethods("GET", "POST", "PATCH", "DELETE") // 허용할 HTTP 메서드
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 실제 파일이 저장된 경로를 사용해 정적 리소스를 처리
        String basePath = System.getProperty("user.dir"); // 현재 작업 디렉토리
        String parentPath = Paths.get(basePath, "../upload").toString(); // 상위 폴더로 이동

        // /uploads/** 경로로 요청 시 해당 경로의 파일을 제공
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + parentPath + "/");
    }
}