package com.example.blog_web.compete.repository;

import com.example.blog_web.compete.domain.CompeteRewardStatus;
import com.example.blog_web.compete.domain.RewardGrant;
import com.example.blog_web.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardGrantRepository extends JpaRepository<RewardGrant, Long> {

    List<RewardGrant> findByUserAndStatusOrderByCreatedAtDesc(User user, CompeteRewardStatus status);

    List<RewardGrant> findByUserOrderByCreatedAtDesc(User user);
}
