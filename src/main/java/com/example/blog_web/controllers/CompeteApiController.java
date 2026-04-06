package com.example.blog_web.controllers;

import com.example.blog_web.compete.domain.CompeteBracket;
import com.example.blog_web.compete.domain.TournamentEnrollment;
import com.example.blog_web.compete.dto.CompeteEnrollmentResponseDto;
import com.example.blog_web.compete.dto.CompeteSummaryResponseDto;
import com.example.blog_web.compete.dto.EnrollRequestDto;
import com.example.blog_web.compete.dto.LeaderboardResponseDto;
import com.example.blog_web.compete.dto.RewardGrantResponseDto;
import com.example.blog_web.compete.dto.UpdateCompeteProfileRequestDto;
import com.example.blog_web.compete.service.CompeteLeaderboardService;
import com.example.blog_web.compete.service.CompeteQueryService;
import com.example.blog_web.compete.service.CompeteWeekService;
import com.example.blog_web.compete.service.RewardService;
import com.example.blog_web.compete.service.TournamentEnrollmentService;
import com.example.blog_web.compete.service.UserCompeteProfileService;
import com.example.blog_web.models.User;
import com.example.blog_web.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * REST surface for weekly tournament / season summaries, enrollment, leaderboard, and rewards.
 */
@RestController
@RequestMapping("/api/compete")
public class CompeteApiController {

    private final CompeteQueryService competeQueryService;
    private final CompeteLeaderboardService leaderboardService;
    private final RewardService rewardService;
    private final UserRepository userRepository;
    private final CompeteWeekService competeWeekService;
    private final UserCompeteProfileService profileService;
    private final TournamentEnrollmentService enrollmentService;

    public CompeteApiController(
            CompeteQueryService competeQueryService,
            CompeteLeaderboardService leaderboardService,
            RewardService rewardService,
            UserRepository userRepository,
            CompeteWeekService competeWeekService,
            UserCompeteProfileService profileService,
            TournamentEnrollmentService enrollmentService) {
        this.competeQueryService = competeQueryService;
        this.leaderboardService = leaderboardService;
        this.rewardService = rewardService;
        this.userRepository = userRepository;
        this.competeWeekService = competeWeekService;
        this.profileService = profileService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/me/summary")
    public ResponseEntity<CompeteSummaryResponseDto> summary(Authentication authentication) {
        requireAuth(authentication);
        return ResponseEntity.ok(competeQueryService.getSummary(authentication.getName()));
    }

    @PostMapping("/me/enroll")
    public ResponseEntity<CompeteEnrollmentResponseDto> enroll(
            @Valid @RequestBody EnrollRequestDto body,
            Authentication authentication) {
        requireAuth(authentication);
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        var week = competeWeekService.getOrCreateCurrentWeek(Instant.now());
        var profile = profileService.getOrCreate(user);
        TournamentEnrollment e = enrollmentService.enroll(user, week, profile, body.optedInLeaderboard());
        return ResponseEntity.ok(toEnrollmentDto(e));
    }

    @PatchMapping("/me/profile")
    public ResponseEntity<Void> updateProfile(
            @RequestBody UpdateCompeteProfileRequestDto body,
            Authentication authentication) {
        requireAuth(authentication);
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        if (body.timezone() != null) {
            profileService.updateTimezone(user, body.timezone());
        }
        if (body.autoEnroll() != null) {
            profileService.updateFlags(user, body.autoEnroll());
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<LeaderboardResponseDto> leaderboard(
            @RequestParam(required = false) Long weekId,
            @RequestParam(required = false) CompeteBracket bracket,
            @RequestParam(required = false) String band,
            Authentication authentication) {
        requireAuth(authentication);
        return ResponseEntity.ok(leaderboardService.getLeaderboard(authentication.getName(), weekId, bracket, band));
    }

    @GetMapping("/rewards")
    public ResponseEntity<java.util.List<RewardGrantResponseDto>> rewards(Authentication authentication) {
        requireAuth(authentication);
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        return ResponseEntity.ok(rewardService.listForUser(user));
    }

    @PostMapping("/rewards/{id}/claim")
    public ResponseEntity<RewardGrantResponseDto> claim(
            @PathVariable("id") Long grantId,
            Authentication authentication) {
        requireAuth(authentication);
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        return ResponseEntity.ok(rewardService.claim(user, grantId));
    }

    private static void requireAuth(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "Unauthorized");
        }
    }

    private static CompeteEnrollmentResponseDto toEnrollmentDto(TournamentEnrollment e) {
        return new CompeteEnrollmentResponseDto(
                e.getId(),
                e.getBracket(),
                e.getTimezoneBand(),
                e.getTpTotal(),
                e.getStatus(),
                e.isOptedInLeaderboard()
        );
    }
}
