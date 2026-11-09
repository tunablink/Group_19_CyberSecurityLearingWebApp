package com.example.blog_web.services;

import com.example.blog_web.models.DashboardResponseDto;
import com.example.blog_web.models.Module;
import com.example.blog_web.models.ProgressSummaryDto;
import com.example.blog_web.models.User;
import com.example.blog_web.repositories.DashboardRepository;
import com.example.blog_web.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service
public class DashboardService {
    private final DashboardRepository dashboardRepository;
    private final UserRepository userRepository;
    private final CyPromProgressService cyPromProgressService;

    public DashboardService(
            DashboardRepository dashboardRepository,
            UserRepository userRepository,
            CyPromProgressService cyPromProgressService
    ) {
        this.dashboardRepository = dashboardRepository;
        this.userRepository = userRepository;
        this.cyPromProgressService = cyPromProgressService;
    }

    public DashboardResponseDto getLearningPathForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(FORBIDDEN, "User is not available."));

        if (!hasLearnerAccess(user.getRoles())) {
            throw new ResponseStatusException(FORBIDDEN, "You do not have permission to view learner dashboard.");
        }

        List<Module> modules = dashboardRepository.findLearningPathByUsername(username);
        ProgressSummaryDto progress = cyPromProgressService.getProgress(username, modules.size());
        Set<Long> completedModuleIds = progress.getCompletedModuleIds().stream()
                .collect(Collectors.toSet());
        int totalModules = modules.size();
        int completedModules = (int) modules.stream()
                .map(Module::getId)
                .filter(completedModuleIds::contains)
                .count();

        int completionPercentage = totalModules == 0 ? 0 : (completedModules * 100) / totalModules;
        String message = totalModules == 0
                ? "You haven't started learning yet. Please select your first module."
                : "Dashboard loaded successfully.";

        List<Module> suggestedNextModules = modules.stream()
                .filter(module -> !completedModuleIds.contains(module.getId()))
                .limit(2)
                .collect(Collectors.toList());

        return new DashboardResponseDto(
                completionPercentage,
                completedModules,
                totalModules,
                message,
                modules,
                suggestedNextModules
        );
    }

    private boolean hasLearnerAccess(String roles) {
        if (roles == null || roles.isBlank()) {
            return false;
        }

        return roles.contains("USER") || roles.contains("LEARNER") || roles.contains("ADMIN");
    }
}
