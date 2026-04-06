package com.example.blog_web.compete.service;

import com.example.blog_web.compete.domain.CompeteBracket;
import com.example.blog_web.compete.domain.CompeteEnrollmentStatus;
import com.example.blog_web.compete.domain.CompeteWeek;
import com.example.blog_web.compete.domain.TournamentEnrollment;
import com.example.blog_web.compete.domain.UserCompeteProfile;
import com.example.blog_web.compete.repository.TournamentEnrollmentRepository;
import com.example.blog_web.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Enrollment lifecycle for a user/week pair (bracket comes from profile or first-time rookie rule).
 */
@Service
public class TournamentEnrollmentService {

    private final TournamentEnrollmentRepository enrollmentRepository;

    public TournamentEnrollmentService(TournamentEnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Transactional(readOnly = true)
    public CompeteBracket resolveBracketForUser(User user, UserCompeteProfile profile) {
        long prior = enrollmentRepository.countByUser(user);
        if (prior == 0) {
            return CompeteBracket.ROOKIE;
        }
        return profile.getBracket();
    }

    @Transactional
    public TournamentEnrollment enroll(User user, CompeteWeek week, UserCompeteProfile profile, boolean optedIn) {
        CompeteBracket bracket = resolveBracketForUser(user, profile);
        return enrollmentRepository.findByUserAndWeek(user, week).map(existing -> {
            existing.setOptedInLeaderboard(optedIn);
            existing.setBracket(bracket);
            existing.setTimezoneBand(profile.getTimezoneBand());
            return enrollmentRepository.save(existing);
        }).orElseGet(() -> {
            TournamentEnrollment e = new TournamentEnrollment(user, week, bracket, profile.getTimezoneBand(), optedIn);
            return enrollmentRepository.save(e);
        });
    }

    /**
     * Creates a row for scoring when {@link UserCompeteProfile#isAutoEnroll()} is true.
     */
    @Transactional
    public TournamentEnrollment ensureForAutoPlay(User user, CompeteWeek week, UserCompeteProfile profile) {
        if (!profile.isAutoEnroll()) {
            return enrollmentRepository.findByUserAndWeek(user, week).orElse(null);
        }
        return enrollmentRepository.findByUserAndWeek(user, week).orElseGet(() -> {
            CompeteBracket bracket = resolveBracketForUser(user, profile);
            TournamentEnrollment e = new TournamentEnrollment(user, week, bracket, profile.getTimezoneBand(), false);
            return enrollmentRepository.save(e);
        });
    }

    @Transactional(readOnly = true)
    public boolean isActive(TournamentEnrollment e) {
        return e != null && e.getStatus() == CompeteEnrollmentStatus.ACTIVE;
    }
}
