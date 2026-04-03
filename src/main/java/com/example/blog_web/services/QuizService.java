package com.example.blog_web.services;

import com.example.blog_web.models.QuizSubmitRequestDto;
import com.example.blog_web.models.QuizSubmitResponseDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QuizService {
    private static final int PASSING_SCORE = 70;
    private final CyPromProgressService cyPromProgressService;

    public QuizService(CyPromProgressService cyPromProgressService) {
        this.cyPromProgressService = cyPromProgressService;
    }

    public QuizSubmitResponseDto submitQuiz(String username, QuizSubmitRequestDto request) {
        // Sample key map; can be replaced by DB-backed quiz/questions later.
        Map<String, String> answerKey = Map.of(
                "Q1", "A",
                "Q2", "B",
                "Q3", "C"
        );

        int totalQuestions = answerKey.size();
        int correct = 0;

        for (Map.Entry<String, String> entry : answerKey.entrySet()) {
            String submitted = request.getAnswers().get(entry.getKey());
            if (entry.getValue().equalsIgnoreCase(submitted)) {
                correct++;
            }
        }

        int score = totalQuestions == 0 ? 0 : (correct * 100) / totalQuestions;
        boolean passed = score >= PASSING_SCORE;

        cyPromProgressService.recordQuizResult(username, request.getModuleId(), score, passed);

        String message = passed
                ? "Quiz passed. Next module can be unlocked."
                : "Quiz not passed. Please review theory and labs, then retry.";

        return new QuizSubmitResponseDto(score, passed, message);
    }
}
