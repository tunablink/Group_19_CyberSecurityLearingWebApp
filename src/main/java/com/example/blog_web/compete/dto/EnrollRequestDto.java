package com.example.blog_web.compete.dto;

import jakarta.validation.constraints.NotNull;

public record EnrollRequestDto(
        @NotNull Boolean optedInLeaderboard
) {
}
