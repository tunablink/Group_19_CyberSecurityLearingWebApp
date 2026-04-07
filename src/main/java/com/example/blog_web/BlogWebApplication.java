package com.example.blog_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Điểm khởi chạy chính của ứng dụng Cybersecurity Learning Web App.
 * Annotation @SpringBootApplication bật tính năng auto-configuration,
 * component scanning và cho phép khai báo thêm bean trong class này.
 */
@SpringBootApplication
@EnableScheduling
public class BlogWebApplication {

	public static void main(String[] args) {
		// Khởi chạy Spring Boot embedded server (Tomcat) trên port 8080
		SpringApplication.run(BlogWebApplication.class, args);
	}

}
