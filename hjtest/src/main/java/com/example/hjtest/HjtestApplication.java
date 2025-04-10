package com.example.hjtest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.hjtest.mapper")
public class HjtestApplication {

	public static void main(String[] args) {
		SpringApplication.run(HjtestApplication.class, args);
	}

}
