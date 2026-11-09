package com.example.blog_web.compete.dto;

import com.example.blog_web.compete.domain.CompeteWeekCloseStatus;

import java.time.Instant;

public record CompeteWeekResponseDto(
        Long id,
        Instant weekStartUtc,
        Instant weekEndUtc,
        Long seasonId,
        String seasonCode,
        int weekIndex,
        boolean frozen,
        CompeteWeekCloseStatus closeStatus
) {
}
