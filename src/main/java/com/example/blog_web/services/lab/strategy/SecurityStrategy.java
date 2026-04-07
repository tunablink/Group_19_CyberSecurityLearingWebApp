package com.example.blog_web.services.lab.strategy;

import com.example.blog_web.models.LabValidationResponseDto;

/**
 * Giao diện lõi đại diện cho một Chiến lược bảo mật (Mô hình Strategy).
 * <p>
 * Mọi kỹ thuật mô phỏng kiểm thử lab (Tấn công, Phòng thủ, Mật mã học...)
 * đều cài đặt interface này để bảo đảm tính đa hình khi đánh giá kết quả lab.
 * </p>
 */
public interface SecurityStrategy {
    LabValidationResponseDto validate(String input);
}
