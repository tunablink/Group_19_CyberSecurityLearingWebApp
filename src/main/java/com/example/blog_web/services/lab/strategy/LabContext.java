package com.example.blog_web.services.lab.strategy;

import com.example.blog_web.models.LabValidationResponseDto;
import org.springframework.stereotype.Component;

/**
 * Thành phần bối cảnh (Context) quản lý việc áp dụng SecurityStrategy.
 * <p>
 * Triển khai mẫu Strategy bằng cách gói gọn quá trình gọi hàm validate 
 * đằng sau một lớp giao tiếp thống nhất này.
 * </p>
 */
@Component
public class LabContext {
    public LabValidationResponseDto execute(SecurityStrategy strategy, String input) {
        return strategy.validate(input);
    }
}
