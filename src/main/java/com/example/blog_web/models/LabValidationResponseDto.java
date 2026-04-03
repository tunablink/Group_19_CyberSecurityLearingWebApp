package com.example.blog_web.models;

public class LabValidationResponseDto {
    private boolean success;
    private String message;
    private String hint;

    public LabValidationResponseDto(boolean success, String message, String hint) {
        this.success = success;
        this.message = message;
        this.hint = hint;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getHint() {
        return hint;
    }
}
