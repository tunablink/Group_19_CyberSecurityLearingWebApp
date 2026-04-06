package com.example.blog_web.controllers;

import com.example.blog_web.compete.domain.CompeteBracket;
import com.example.blog_web.compete.domain.CompeteWeekCloseStatus;
import com.example.blog_web.compete.dto.CompeteCapsDto;
import com.example.blog_web.compete.dto.CompeteSeasonSummaryDto;
import com.example.blog_web.compete.dto.CompeteSummaryResponseDto;
import com.example.blog_web.compete.dto.CompeteWeekResponseDto;
import com.example.blog_web.compete.dto.LeaderboardResponseDto;
import com.example.blog_web.compete.dto.RewardGrantResponseDto;
import com.example.blog_web.compete.service.CompeteLeaderboardService;
import com.example.blog_web.compete.service.CompeteQueryService;
import com.example.blog_web.compete.service.RewardService;
import com.example.blog_web.models.User;
import com.example.blog_web.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

/**
 * Server-rendered Compete hub (weekly tournament / season). API details live under {@code /api/compete/**}.
 */
@Controller
public class CompeteController {

    private static final Logger log = LoggerFactory.getLogger(CompeteController.class);

    private final CompeteQueryService competeQueryService;
    private final CompeteLeaderboardService leaderboardService;
    private final RewardService rewardService;
    private final UserRepository userRepository;

    public CompeteController(
            CompeteQueryService competeQueryService,
            CompeteLeaderboardService leaderboardService,
            RewardService rewardService,
            UserRepository userRepository) {
        this.competeQueryService = competeQueryService;
        this.leaderboardService = leaderboardService;
        this.rewardService = rewardService;
        this.userRepository = userRepository;
    }

    @GetMapping("/compete")
    public String competePage(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        model.addAttribute("username", username);
        try {
            model.addAttribute("competeSummary", competeQueryService.getSummary(username));
        } catch (Exception ex) {
            log.error("Failed to load compete summary for user {}", username, ex);
            model.addAttribute("competeSummary", fallbackSummary());
            model.addAttribute("competeLoadError", "Khong tai duoc du lieu giai dau. Da hien thi du lieu tam thoi.");
        }
        return "compete/compete";
    }

    @GetMapping("/compete/leaderboard")
    public String leaderboardPage(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        try {
            LeaderboardResponseDto board = leaderboardService.getLeaderboard(principal.getName(), null, null, null);
            model.addAttribute("board", board);
        } catch (Exception ex) {
            log.error("Failed to load compete leaderboard for user {}", principal.getName(), ex);
            model.addAttribute("board", fallbackBoard());
            model.addAttribute("competeLoadError", "Khong tai duoc leaderboard. Dang hien thi trang trong.");
        }
        return "compete/leaderboard";
    }

    @GetMapping("/compete/rewards")
    public String rewardsPage(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        try {
            User user = userRepository.findByUsername(principal.getName()).orElseThrow();
            List<RewardGrantResponseDto> rewards = rewardService.listForUser(user);
            model.addAttribute("rewards", rewards);
        } catch (Exception ex) {
            log.error("Failed to load compete rewards for user {}", principal.getName(), ex);
            model.addAttribute("rewards", List.of());
            model.addAttribute("competeLoadError", "Khong tai duoc rewards. Dang hien thi trang trong.");
        }
        return "compete/rewards";
    }

    private static CompeteSummaryResponseDto fallbackSummary() {
        Instant now = Instant.now();
        Instant weekStart = now.minusSeconds(2 * 24 * 60 * 60);
        Instant weekEnd = now.plusSeconds(5 * 24 * 60 * 60);
        return new CompeteSummaryResponseDto(
                new CompeteWeekResponseDto(null, weekStart, weekEnd, null, "MVP-2026", 1, false, CompeteWeekCloseStatus.OPEN),
                null,
                new CompeteCapsDto(0, 120, 0, 600),
                new CompeteSeasonSummaryDto("MVP-2026", 0, 1)
        );
    }

    private static LeaderboardResponseDto fallbackBoard() {
        return new LeaderboardResponseDto(null, CompeteBracket.CORE, "AM2", List.of());
    }
}
