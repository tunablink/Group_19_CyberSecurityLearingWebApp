package com.example.blog_web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Controller hướng dẫn người dùng qua các nội dung giáo dục cốt lõi.
 * <p>
 * Xử lý các request điều hướng cho learning hub, các bài học về lỗ hổng bảo mật cụ thể,
 * bản xem trước bài học và các câu hỏi quiz tương ứng.
 * </p>
 */
@Controller
public class LearnController {

    @GetMapping("/learn")
    public String learn() {
        return "learn/learn";
    }

    @GetMapping("/learn/{id}")
    public String lesson(@PathVariable String id) {
        return "learn/" + id;
    }

    @GetMapping("/learn/preview/{id}")
    public String preview(@PathVariable String id) {
        return "learn/preview" + id;
    }

    @GetMapping("/learn/question/{id}")
    public String question(@PathVariable String id) {
        return "learn/question" + id;
    }
}
