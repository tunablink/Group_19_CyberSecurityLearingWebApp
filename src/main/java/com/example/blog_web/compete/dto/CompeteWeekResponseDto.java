package com.example.blog_web.compete.dto;

import com.example.blog_web.compete.domain.CompeteWeekCloseStatus;

import java.time.Instant;

/**
 * Serializable week boundary for clients (countdowns, labels).
 */
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
