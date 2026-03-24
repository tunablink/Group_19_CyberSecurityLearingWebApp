package com.example.blog_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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