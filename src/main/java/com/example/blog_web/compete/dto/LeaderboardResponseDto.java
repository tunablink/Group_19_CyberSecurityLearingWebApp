package com.example.blog_web.compete.dto;

import com.example.blog_web.compete.domain.CompeteBracket;

import java.util.List;

public record LeaderboardResponseDto(
        Long weekId,
        CompeteBracket bracket,
        String timezoneBand,
        List<LeaderboardRowResponseDto> entries
) {
}
