package com.example.blog_web.controllers;

import com.example.blog_web.models.QuizSubmitRequestDto;
import com.example.blog_web.models.QuizSubmitResponseDto;
import com.example.blog_web.services.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * REST Endpoint chịu trách nhiệm xử lý trả lời câu hỏi Quiz và chấm điểm.
 * <p>
 * Nhận đáp án nộp từ giao diện học viên, gọi dịch vụ phía backend để tính toán điểm số,
 * ghi lại kết quả và phản hồi lập tức trạng thái pass/fail.
 * </p>
 */
@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/submit")
    public ResponseEntity<QuizSubmitResponseDto> submitQuiz(@Valid @RequestBody QuizSubmitRequestDto request,
                                                            Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "Unauthorized");
        }
        QuizSubmitResponseDto response = quizService.submitQuiz(authentication.getName(), request);
        return ResponseEntity.ok(response);
    }
}
