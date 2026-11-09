package com.example.blog_web.compete.dto;

public record CompeteSummaryResponseDto(
        CompeteWeekResponseDto week,
        CompeteEnrollmentResponseDto enrollment,
        CompeteCapsDto caps,
        CompeteSeasonSummaryDto season
) {
}
