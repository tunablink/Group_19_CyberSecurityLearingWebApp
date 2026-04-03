package com.example.blog_web.services.lab.strategy;

import com.example.blog_web.models.LabValidationResponseDto;
import org.springframework.stereotype.Component;

@Component
public class LabContext {
    public LabValidationResponseDto execute(SecurityStrategy strategy, String input) {
        return strategy.validate(input);
    }
}
