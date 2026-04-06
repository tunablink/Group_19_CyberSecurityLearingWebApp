package com.example.blog_web.compete.repository;

import com.example.blog_web.compete.domain.CompeteSeason;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompeteSeasonRepository extends JpaRepository<CompeteSeason, Long> {
    Optional<CompeteSeason> findFirstByOrderByStartsAtUtcAsc();

    Optional<CompeteSeason> findFirstByOrderByStartsAtUtcDesc();
}
