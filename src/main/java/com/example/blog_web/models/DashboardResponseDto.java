package com.example.blog_web.models;

import java.util.List;

/**
 * Data Transfer Object (DTO) dùng để trả về thông tin dashboard cho client.
 * <p>
 * Lưu trữ % tiến độ của học viên, danh sách toàn bộ các module và đề xuất
 * module tiếp theo hiển thị trên giao diện người học (Learner Dashboard).
 * </p>
 */
public class DashboardResponseDto {
    private int completionPercentage;
    private int completedModules;
    private int totalModules;
    private String message;
    private List<Module> modules;
    private List<Module> suggestedNextModules;

    public DashboardResponseDto(int completionPercentage,
                                int completedModules,
                                int totalModules,
                                String message,
                                List<Module> modules,
                                List<Module> suggestedNextModules) {
        this.completionPercentage = completionPercentage;
        this.completedModules = completedModules;
        this.totalModules = totalModules;
        this.message = message;
        this.modules = modules;
        this.suggestedNextModules = suggestedNextModules;
    }

    public int getCompletionPercentage() {
        return completionPercentage;
    }

    public int getCompletedModules() {
        return completedModules;
    }

    public int getTotalModules() {
        return totalModules;
    }

    public String getMessage() {
        return message;
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getSuggestedNextModules() {
        return suggestedNextModules;
    }
}
