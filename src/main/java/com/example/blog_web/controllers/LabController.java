package com.example.blog_web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller thao tác hiển thị giao diện tương tác (Interactive Laboratories).
 * <p>
 * Quản lý routing các view của màn hình "Threat Hunting Simulator".
 * </p>
 */
@Controller
public class LabController {

    @GetMapping("/lab")
    public String lab() {
        return "lab/lab";
    }
}
