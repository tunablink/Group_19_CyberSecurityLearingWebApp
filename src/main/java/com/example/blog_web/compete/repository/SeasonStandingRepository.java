package com.example.blog_web.compete.repository;

import com.example.blog_web.compete.domain.SeasonStanding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeasonStandingRepository extends JpaRepository<SeasonStanding, SeasonStanding.SeasonStandingId> {

    Optional<SeasonStanding> findByUserIdAndSeasonId(Long userId, Long seasonId);
}
