package com.example.blog_web.compete.dto;

public record UpdateCompeteProfileRequestDto(
        String timezone,
        Boolean autoEnroll
) {
}
