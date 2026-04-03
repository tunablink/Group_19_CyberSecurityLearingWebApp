package com.example.blog_web.controllers;

import com.example.blog_web.models.DashboardResponseDto;
import com.example.blog_web.services.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardApiController {

    private final DashboardService dashboardService;

    public DashboardApiController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/learning-path")
    public ResponseEntity<DashboardResponseDto> getDashboardLearningPath(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "Unauthorized");
        }
        DashboardResponseDto response = dashboardService.getLearningPathForUser(authentication.getName());
        return ResponseEntity.ok(response);
    }
}
