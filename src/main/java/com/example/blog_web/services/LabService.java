package com.example.blog_web.services;

import com.example.blog_web.models.LabValidationResponseDto;
import com.example.blog_web.services.lab.factory.LabValidatorFactory;
import com.example.blog_web.services.lab.strategy.LabContext;
import com.example.blog_web.services.lab.strategy.SecurityStrategy;
import org.springframework.stereotype.Service;

@Service
public class LabService {
    private final LabValidatorFactory labValidatorFactory;
    private final LabContext labContext;

    public LabService(LabValidatorFactory labValidatorFactory, LabContext labContext) {
        this.labValidatorFactory = labValidatorFactory;
        this.labContext = labContext;
    }

    public LabValidationResponseDto validateLab(String labType, String input) {
        SecurityStrategy strategy = labValidatorFactory.createValidator(labType);
        return labContext.execute(strategy, input);
    }
}
