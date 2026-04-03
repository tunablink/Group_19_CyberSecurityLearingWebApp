package com.example.blog_web.models;

import java.util.List;

public class ProgressSummaryDto {
    private int completionPercentage;
    private int totalQuizAttempts;
    private int averageScore;
    private List<Long> completedModuleIds;

    public ProgressSummaryDto(int completionPercentage, int totalQuizAttempts, int averageScore, List<Long> completedModuleIds) {
        this.completionPercentage = completionPercentage;
        this.totalQuizAttempts = totalQuizAttempts;
        this.averageScore = averageScore;
        this.completedModuleIds = completedModuleIds;
    }

    public int getCompletionPercentage() {
        return completionPercentage;
    }

    public int getTotalQuizAttempts() {
        return totalQuizAttempts;
    }

    public int getAverageScore() {
        return averageScore;
    }

    public List<Long> getCompletedModuleIds() {
        return completedModuleIds;
    }
}
