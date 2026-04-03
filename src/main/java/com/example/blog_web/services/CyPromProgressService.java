package com.example.blog_web.services;

import com.example.blog_web.models.ProgressSummaryDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CyPromProgressService {
    private final Map<String, List<Integer>> quizScoresByUser = new ConcurrentHashMap<>();
    private final Map<String, List<Long>> completedModulesByUser = new ConcurrentHashMap<>();

    public void recordQuizResult(String username, Long moduleId, int score, boolean passed) {
        quizScoresByUser.computeIfAbsent(username, key -> new ArrayList<>()).add(score);

        if (passed) {
            completedModulesByUser.computeIfAbsent(username, key -> new ArrayList<>());
            List<Long> modules = completedModulesByUser.get(username);
            if (!modules.contains(moduleId)) {
                modules.add(moduleId);
                modules.sort(Comparator.naturalOrder());
            }
        }
    }

    public ProgressSummaryDto getProgress(String username, int totalModules) {
        List<Integer> scores = quizScoresByUser.getOrDefault(username, List.of());
        List<Long> completed = completedModulesByUser.getOrDefault(username, List.of());

        int totalAttempts = scores.size();
        int averageScore = totalAttempts == 0
                ? 0
                : (int) Math.round(scores.stream().mapToInt(Integer::intValue).average().orElse(0));
        int completionPercentage = totalModules == 0
                ? 0
                : (completed.size() * 100) / totalModules;

        return new ProgressSummaryDto(completionPercentage, totalAttempts, averageScore, completed);
    }
}
