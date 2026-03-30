package com.example.blog_web.controller;

import com.example.blog_web.model.Module;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ModuleController {

    @GetMapping("/modules")
    public List<Module> getModules() {
        // TODO get from DB
        // ADD JWT auth
        return SampleModulesList();
    }

    private List<Module> SampleModulesList() {
        return Arrays.asList(
                new Module(1L, "Introduction to Cybersecurity", "Learn the basics of cybersecurity.", "Completed"),
                new Module(2L, "Web Application Security", "Explore common web vulnerabilities.", "In Progress"),
                new Module(3L, "Network Security Fundamentals", "Understand network defense mechanisms.", "Not Started"),
                new Module(4L, "Cryptography Basics", "Dive into encryption and hashing.", "Not Started")
        );
    }
}
