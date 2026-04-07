package com.example.blog_web.services;

import com.example.blog_web.models.LabValidationResponseDto;
import com.example.blog_web.services.lab.factory.LabValidatorFactory;
import com.example.blog_web.services.lab.strategy.LabContext;
import com.example.blog_web.services.lab.strategy.SecurityStrategy;
import org.springframework.stereotype.Service;

/**
 * Service đóng vai trò xử lý nghiệp vụ xác thực (Validation) cho các bài thực hành Lab.
 * <p>
 * Dựa vào Factory pattern để điều hướng luồng xử lý payload của người dùng đền đúng 
 * logic xác thực tương ứng (như tấn công, phòng thủ).
 * </p>
 */
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
