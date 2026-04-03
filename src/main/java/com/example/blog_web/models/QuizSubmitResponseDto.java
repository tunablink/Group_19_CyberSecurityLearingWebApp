package com.example.blog_web.models;

public class QuizSubmitResponseDto {
    private int score;
    private boolean passed;
    private String message;

    public QuizSubmitResponseDto(int score, boolean passed, String message) {
        this.score = score;
        this.passed = passed;
        this.message = message;
    }

    public int getScore() {
        return score;
    }

    public boolean isPassed() {
        return passed;
    }

    public String getMessage() {
        return message;
    }
}
