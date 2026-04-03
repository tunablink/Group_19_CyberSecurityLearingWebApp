package com.example.blog_web.models;

import jakarta.validation.constraints.NotNull;

import java.util.Map;

public class QuizSubmitRequestDto {
    @NotNull(message = "Module id is required")
    private Long moduleId;

    @NotNull(message = "Answers are required")
    private Map<String, String> answers;

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Map<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, String> answers) {
        this.answers = answers;
    }
}
