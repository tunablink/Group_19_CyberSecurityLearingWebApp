package com.example.blog_web.services.lab.strategy;

import com.example.blog_web.models.LabValidationResponseDto;

public interface SecurityStrategy {
    LabValidationResponseDto validate(String input);
}
