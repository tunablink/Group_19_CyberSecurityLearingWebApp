package com.example.blog_web.compete.dto;

import com.example.blog_web.compete.domain.CompeteRewardStatus;

import java.time.Instant;

public record RewardGrantResponseDto(
        Long id,
        String tier,
        CompeteRewardStatus status,
        String payload,
        Instant expiresAt,
        Long weekId,
        Long seasonId
) {
}
