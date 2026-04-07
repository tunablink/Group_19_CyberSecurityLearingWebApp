package com.example.blog_web.models;

/**
 * Data Transfer Object (DTO) trả về kết quả bài quiz.
 * <p>
 * Mang theo điểm số tính toán được, trạng thái đánh giá qua/trượt (pass/fail)
 * và thông điệp hướng dẫn bước tiếp theo cho người dùng.
 * </p>
 */
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
