package com.example.blog_web.services;

import com.example.blog_web.models.DashboardResponseDto;
import com.example.blog_web.models.Module;
import com.example.blog_web.models.User;
import com.example.blog_web.repositories.DashboardRepository;
import com.example.blog_web.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;

/**
 * Service cung cấp dữ liệu cho hiển thị Dashboard thông minh.
 * <p>
 * Tính toán tỷ lệ hoàn thành, lấy tiến trình học tập, và gợi ý các lộ trình
 * tiếp theo dựa vào số lượng học phần chưa được hoàn tất.
 * </p>
 */
@Service
public class DashboardService {
    private final DashboardRepository dashboardRepository;
    private final UserRepository userRepository;

    public DashboardService(DashboardRepository dashboardRepository, UserRepository userRepository) {
        this.dashboardRepository = dashboardRepository;
        this.userRepository = userRepository;
    }

    public DashboardResponseDto getLearningPathForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(FORBIDDEN, "User is not available."));

        if (!hasLearnerAccess(user.getRoles())) {
            throw new ResponseStatusException(FORBIDDEN, "You do not have permission to view learner dashboard.");
        }

        List<Module> modules = dashboardRepository.findLearningPathByUsername(username);
        int totalModules = modules.size();
        int completedModules = (int) modules.stream()
                .filter(module -> "Completed".equalsIgnoreCase(module.getStatus()))
                .count();

        int completionPercentage = totalModules == 0 ? 0 : (completedModules * 100) / totalModules;
        String message = totalModules == 0
                ? "You haven't started learning yet. Please select your first module."
                : "Dashboard loaded successfully.";

        List<Module> suggestedNextModules = modules.stream()
                .filter(module -> !"Completed".equalsIgnoreCase(module.getStatus()))
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

        // Support both legacy USER role and explicit LEARNER role.
        return roles.contains("USER") || roles.contains("LEARNER");
    }
}
