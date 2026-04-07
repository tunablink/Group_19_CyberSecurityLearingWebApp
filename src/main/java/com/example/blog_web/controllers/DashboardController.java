package com.example.blog_web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller trả về giao diện Dashboard chính cho người dùng.
 * <p>
 * Cung cấp các route web để điều hướng người dùng (đã đăng nhập) đến trung tâm học tập (learning hub).
 * </p>
 */
@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard/dashboard";
    }
}
