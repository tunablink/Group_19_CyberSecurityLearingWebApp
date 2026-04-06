package com.example.blog_web.compete.dto;

import com.example.blog_web.compete.domain.CompeteBracket;
import com.example.blog_web.compete.domain.CompeteEnrollmentStatus;

public record CompeteEnrollmentResponseDto(
        Long id,
        CompeteBracket bracket,
        String timezoneBand,
        int tpTotal,
        CompeteEnrollmentStatus status,
        boolean optedInLeaderboard
) {
}
