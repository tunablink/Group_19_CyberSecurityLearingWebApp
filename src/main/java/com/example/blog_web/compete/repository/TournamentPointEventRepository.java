package com.example.blog_web.compete.repository;

import com.example.blog_web.compete.domain.CompeteWeek;
import com.example.blog_web.compete.domain.TournamentPointEvent;
import com.example.blog_web.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface TournamentPointEventRepository extends JpaRepository<TournamentPointEvent, Long> {

    boolean existsByIdempotencyKey(String idempotencyKey);

    Optional<TournamentPointEvent> findByIdempotencyKey(String idempotencyKey);

    long countByUserAndWeekAndSourceKey(User user, CompeteWeek week, String sourceKey);

    @Query("""
            SELECT COALESCE(SUM(e.deltaTp), 0) FROM TournamentPointEvent e
            WHERE e.user = :user AND e.createdAt >= :since
            """)
    int sumDeltaTpSince(@Param("user") User user, @Param("since") Instant since);
}
