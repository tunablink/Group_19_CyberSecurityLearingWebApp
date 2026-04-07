package com.example.blog_web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.blog_web.models.User;
import com.example.blog_web.services.UserService;
import org.springframework.ui.Model;

import java.security.Principal;

/**
 * Controller quản lý hồ sơ người dùng.
 * <p>
 * Quản lý trang "Member Home", cung cấp các chi tiết và dữ liệu cá nhân của
 * người dùng (đã xác thực) đổ vào giao diện hiển thị.
 * </p>
 */
@Controller
public class MemberController {

    private final UserService userService;

    public MemberController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/member/home")
    public String memberHome(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        return "member-home";
    }
}
