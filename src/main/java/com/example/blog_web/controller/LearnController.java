package com.example.blog_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
