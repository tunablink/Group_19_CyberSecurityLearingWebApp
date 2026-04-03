package com.example.blog_web.services.lab.factory;

import com.example.blog_web.services.lab.strategy.DefenseStrategy;
import com.example.blog_web.services.lab.strategy.ExploitStrategy;
import com.example.blog_web.services.lab.strategy.SecurityStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class LabValidatorFactory {
    private final ExploitStrategy exploitStrategy;
    private final DefenseStrategy defenseStrategy;

    public LabValidatorFactory(ExploitStrategy exploitStrategy, DefenseStrategy defenseStrategy) {
        this.exploitStrategy = exploitStrategy;
        this.defenseStrategy = defenseStrategy;
    }

    public SecurityStrategy createValidator(String labType) {
        if (labType == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Lab type is required");
        }

        return switch (labType.trim().toUpperCase()) {
            case "EXPLOIT" -> exploitStrategy;
            case "DEFENSE" -> defenseStrategy;
            default -> throw new ResponseStatusException(BAD_REQUEST, "Unsupported lab type");
        };
    }
}
