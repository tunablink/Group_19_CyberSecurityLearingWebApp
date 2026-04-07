package com.example.blog_web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Controller điều hướng trọng tâm cho các màn hình công khai của ứng dụng.
 * <p>
 * Phục vụ các route có thể truy cập không cần đăng nhập như trang chủ (landing page),
 * trang đăng nhập, và các bài học Demo dành cho Client dạng Guest.
 * </p>
 */
@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/learn-guest")
    public String learnGuest() {
        return "learn_guest/learn";
    }

    @GetMapping("/learn-guest/{id}")
    public String learnGuestLesson(@PathVariable String id) {
        return "learn_guest/" + id;
    }
}