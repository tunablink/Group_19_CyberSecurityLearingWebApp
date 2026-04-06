package com.example.blog_web.compete.service;

import com.example.blog_web.compete.domain.CompeteBracket;
import com.example.blog_web.compete.domain.CompeteEnrollmentStatus;
import com.example.blog_web.compete.domain.CompeteWeek;
import com.example.blog_web.compete.domain.TournamentEnrollment;
import com.example.blog_web.compete.domain.UserCompeteProfile;
import com.example.blog_web.compete.dto.LeaderboardResponseDto;
import com.example.blog_web.compete.dto.LeaderboardRowResponseDto;
import com.example.blog_web.compete.repository.CompeteWeekRepository;
import com.example.blog_web.compete.repository.TournamentEnrollmentRepository;
import com.example.blog_web.models.User;
import com.example.blog_web.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Read model for cohort leaderboard (opted-in users only).
 */
@Service
public class CompeteLeaderboardService {

    private final UserRepository userRepository;
    private final CompeteWeekRepository weekRepository;
    private final CompeteWeekService competeWeekService;
    private final TournamentEnrollmentRepository enrollmentRepository;
    private final UserCompeteProfileService profileService;

    public CompeteLeaderboardService(
            UserRepository userRepository,
            CompeteWeekRepository weekRepository,
            CompeteWeekService competeWeekService,
            TournamentEnrollmentRepository enrollmentRepository,
            UserCompeteProfileService profileService) {
        this.userRepository = userRepository;
        this.weekRepository = weekRepository;
        this.competeWeekService = competeWeekService;
        this.enrollmentRepository = enrollmentRepository;
        this.profileService = profileService;
    }

    @Transactional(readOnly = true)
    public LeaderboardResponseDto getLeaderboard(String username, Long weekId, CompeteBracket bracket, String band) {
        User me = userRepository.findByUsername(username).orElseThrow();
        CompeteWeek week = weekId != null
                ? weekRepository.findById(weekId).orElseThrow()
                : competeWeekService.getOrCreateCurrentWeek(Instant.now());
        UserCompeteProfile profile = profileService.getOrCreate(me);
        CompeteBracket b = bracket != null ? bracket : profile.getBracket();
        String tzBand = band != null ? band : profile.getTimezoneBand();

        List<TournamentEnrollment> cohort = enrollmentRepository.findLeaderboardCohort(
                week, CompeteEnrollmentStatus.ACTIVE, b, tzBand);

        List<LeaderboardRowResponseDto> rows = new ArrayList<>();
        int rank = 1;
        for (TournamentEnrollment e : cohort) {
            String name = e.getUser().getUsername();
            rows.add(new LeaderboardRowResponseDto(rank++, e.getUser().getId(), name, e.getTpTotal(), e.getBracket()));
        }
        return new LeaderboardResponseDto(week.getId(), b, tzBand, rows);
    }
}
