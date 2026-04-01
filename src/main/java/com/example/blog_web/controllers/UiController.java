package com.example.blog_web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {

    @GetMapping("/ui-components")
    public String showUiComponents(Model model) {
        model.addAttribute("successMessage", "This is a demonstration of a success alert!");
        model.addAttribute("infoMessage", "This is a demonstration of an info alert.");
        model.addAttribute("errorMessage", "This is a demonstration of an error alert.");
        return "ui-components";
    }
}
