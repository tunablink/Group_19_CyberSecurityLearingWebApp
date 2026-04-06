package com.example.blog_web.compete.service;

import com.example.blog_web.compete.domain.CompeteTpSourceType;
import com.example.blog_web.compete.domain.CompeteWeek;
import com.example.blog_web.compete.domain.TournamentEnrollment;
import com.example.blog_web.compete.domain.TournamentPointEvent;
import com.example.blog_web.compete.domain.UserCompeteProfile;
import com.example.blog_web.compete.dto.TpAwardResponseDto;
import com.example.blog_web.compete.repository.TournamentEnrollmentRepository;
import com.example.blog_web.compete.repository.TournamentPointEventRepository;
import com.example.blog_web.compete.support.CompeteUtcTime;
import com.example.blog_web.models.User;
import com.example.blog_web.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Awards TP after legitimate quiz/lab success. Idempotent per {@code idempotencyKey}.
 * Does not validate quiz answers or lab payloads — callers must only invoke on success.
 */
@Service
public class TournamentPointService {

    public static final int BASE_QUIZ_TP = 10;
    public static final int BASE_LAB_TP = 25;
    public static final int DAILY_TP_CAP = 120;
    public static final int WEEKLY_HARD_CAP = 600;

    private final UserRepository userRepository;
    private final CompeteWeekService competeWeekService;
    private final UserCompeteProfileService profileService;
    private final TournamentEnrollmentService enrollmentService;
    private final TournamentEnrollmentRepository enrollmentRepository;
    private final TournamentPointEventRepository eventRepository;

    public TournamentPointService(
            UserRepository userRepository,
            CompeteWeekService competeWeekService,
            UserCompeteProfileService profileService,
            TournamentEnrollmentService enrollmentService,
            TournamentEnrollmentRepository enrollmentRepository,
            TournamentPointEventRepository eventRepository) {
        this.userRepository = userRepository;
        this.competeWeekService = competeWeekService;
        this.profileService = profileService;
        this.enrollmentService = enrollmentService;
        this.enrollmentRepository = enrollmentRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public TpAwardResponseDto recordAfterQuizPass(String username, Long moduleId) {
        User user = userRepository.findByUsername(username).orElseThrow();
        CompeteWeek week = competeWeekService.getOrCreateCurrentWeek(Instant.now());
        String sourceKey = "module:" + moduleId;
        String idempotencyKey = "quiz:" + user.getId() + ":m:" + moduleId + ":w:" + week.getId();
        return applyAward(user, week, CompeteTpSourceType.QUIZ_MODULE, sourceKey, BASE_QUIZ_TP, idempotencyKey);
    }

    @Transactional
    public TpAwardResponseDto recordAfterLabPass(String username, String labType) {
        User user = userRepository.findByUsername(username).orElseThrow();
        CompeteWeek week = competeWeekService.getOrCreateCurrentWeek(Instant.now());
        String sourceKey = "lab:" + labType;
        String idempotencyKey = "lab:" + user.getId() + ":l:" + labType + ":w:" + week.getId();
        return applyAward(user, week, CompeteTpSourceType.LAB, sourceKey, BASE_LAB_TP, idempotencyKey);
    }

    private TpAwardResponseDto applyAward(
            User user,
            CompeteWeek week,
            CompeteTpSourceType sourceType,
            String sourceKey,
            int baseTp,
            String idempotencyKey) {

        if (eventRepository.existsByIdempotencyKey(idempotencyKey)) {
            TournamentPointEvent prior = eventRepository.findByIdempotencyKey(idempotencyKey).orElseThrow();
            TournamentEnrollment en = enrollmentRepository.findById(prior.getEnrollment().getId()).orElseThrow();
            return new TpAwardResponseDto(en.getId(), en.getTpTotal(), 0, false);
        }

        Instant now = Instant.now();
        if (week.getCloseStatus() != com.example.blog_web.compete.domain.CompeteWeekCloseStatus.OPEN
                || competeWeekService.isWeekClosedForScoring(week, now)) {
            return new TpAwardResponseDto(0, 0, 0, false);
        }

        UserCompeteProfile profile = profileService.getOrCreate(user);
        TournamentEnrollment enrollment = enrollmentService.ensureForAutoPlay(user, week, profile);
        if (enrollment == null || !enrollmentService.isActive(enrollment)) {
            return new TpAwardResponseDto(0, 0, 0, false);
        }

        long priorAttempts = eventRepository.countByUserAndWeekAndSourceKey(user, week, sourceKey);
        int raw;
        if (priorAttempts == 0) {
            raw = baseTp;
        } else if (priorAttempts == 1) {
            raw = Math.max(1, baseTp / 4);
        } else {
            raw = 0;
        }

        Instant dayStart = CompeteUtcTime.startOfUtcDay(now);
        int dailyUsed = eventRepository.sumDeltaTpSince(user, dayStart);
        int remainingDaily = Math.max(0, DAILY_TP_CAP - dailyUsed);
        int remainingWeekly = Math.max(0, WEEKLY_HARD_CAP - enrollment.getTpTotal());
        int delta = Math.min(raw, Math.min(remainingDaily, remainingWeekly));

        if (delta <= 0) {
            TournamentPointEvent z = new TournamentPointEvent(enrollment, user, week, sourceType, sourceKey, 0, idempotencyKey);
            eventRepository.save(z);
            return new TpAwardResponseDto(enrollment.getId(), enrollment.getTpTotal(), 0, false);
        }

        enrollment.setTpTotal(enrollment.getTpTotal() + delta);
        if (enrollment.getFirstTpAt() == null) {
            enrollment.setFirstTpAt(now);
        }
        enrollmentRepository.save(enrollment);

        TournamentPointEvent ev = new TournamentPointEvent(enrollment, user, week, sourceType, sourceKey, delta, idempotencyKey);
        eventRepository.save(ev);

        return new TpAwardResponseDto(enrollment.getId(), enrollment.getTpTotal(), delta, true);
    }
}
