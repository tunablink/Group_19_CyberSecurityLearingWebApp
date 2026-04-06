package com.example.blog_web.compete.dto;

import com.example.blog_web.compete.domain.CompeteBracket;

public record LeaderboardRowResponseDto(
        int rank,
        long userId,
        String displayName,
        int tpTotal,
        CompeteBracket bracket
) {
}
