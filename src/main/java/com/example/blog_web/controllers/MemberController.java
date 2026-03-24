package com.example.blog_web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
    @GetMapping("/member/home")
    public String memberHome() {
        return "member-home";
    }
}