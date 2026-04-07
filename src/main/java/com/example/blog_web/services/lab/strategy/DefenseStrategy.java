package com.example.blog_web.services.lab.strategy;

import com.example.blog_web.models.LabValidationResponseDto;
import org.springframework.stereotype.Component;

/**
 * Chiến lược kiểm tra bài thực hành phòng ngự (Defense).
 * <p>
 * Phân tích mã nguồn do người dùng nhập vào để tìm kiếm các kỹ thuật ngăn chặn
 * lỗ hổng đã được biết tới (ví dụ: Prepared Statements, Output Encoding).
 * </p>
 */
@Component("defenseStrategy")
public class DefenseStrategy implements SecurityStrategy {
    @Override
    public LabValidationResponseDto validate(String input) {
        boolean hasParameterizedQueryHint = input != null && input.toLowerCase().contains("preparedstatement");
        boolean hasOutputEncodingHint = input != null && input.toLowerCase().contains("htmlspecialchars");
        boolean success = hasParameterizedQueryHint || hasOutputEncodingHint;

        if (success) {
            return new LabValidationResponseDto(true, "Defense applied successfully.", "Good job! Your mitigation pattern is accepted.");
        }
        return new LabValidationResponseDto(false, "Defense is incomplete.", "Use parameterized queries or output encoding.");
    }
}
