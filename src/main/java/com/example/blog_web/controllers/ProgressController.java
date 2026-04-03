package com.example.blog_web.controllers;

import com.example.blog_web.models.ProgressSummaryDto;
import com.example.blog_web.services.CyPromProgressService;
import com.example.blog_web.services.ModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {
    private final CyPromProgressService cyPromProgressService;
    private final ModuleService moduleService;

    public ProgressController(CyPromProgressService cyPromProgressService, ModuleService moduleService) {
        this.cyPromProgressService = cyPromProgressService;
        this.moduleService = moduleService;
    }

    @GetMapping("/me")
    public ResponseEntity<ProgressSummaryDto> getMyProgress(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "Unauthorized");
        }

        int totalModules = moduleService.getAllModules().size();
        ProgressSummaryDto response = cyPromProgressService.getProgress(authentication.getName(), totalModules);
        return ResponseEntity.ok(response);
    }
}
