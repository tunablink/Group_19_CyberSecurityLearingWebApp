package com.example.blog_web.models;

/**
 * Data Transfer Object (DTO) trả về kết quả xác thực phòng Lab.
 * <p>
 * Trả lời cho client biết submission thành công hay thất bại, kèm theo 
 * tin nhắn giải thích và gợi ý sửa lỗi (hint) nếu có.
 * </p>
 */
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
