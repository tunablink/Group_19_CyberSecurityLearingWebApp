package com.example.blog_web.compete.dto;

public record CompeteCapsDto(
        int dailyTpUsed,
        int dailyTpCap,
        int weeklyTpTotal,
        int weeklyHardCap
) {
}
