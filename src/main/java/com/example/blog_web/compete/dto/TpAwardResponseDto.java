package com.example.blog_web.compete.dto;

public record TpAwardResponseDto(
        long enrollmentId,
        int tpTotal,
        int deltaTp,
        boolean awarded
) {
}
