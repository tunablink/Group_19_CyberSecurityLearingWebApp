package com.example.blog_web.repositories;

import com.example.blog_web.models.Module;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class ModuleRepository {

    public List<Module> findAll() {
        // Temporary in-memory source to preserve behavior before full DB migration.
        return Arrays.asList(
                new Module(1L, "Introduction to Cybersecurity", "Learn the basics of cybersecurity.", "Completed"),
                new Module(2L, "Web Application Security", "Explore common web vulnerabilities.", "In Progress"),
                new Module(3L, "Network Security Fundamentals", "Understand network defense mechanisms.", "Not Started"),
                new Module(4L, "Cryptography Basics", "Dive into encryption and hashing.", "Not Started")
        );
    }
}
