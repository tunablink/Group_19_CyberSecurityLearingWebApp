package com.example.blog_web.compete.repository;

import com.example.blog_web.compete.domain.UserCompeteProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCompeteProfileRepository extends JpaRepository<UserCompeteProfile, Long> {
}
