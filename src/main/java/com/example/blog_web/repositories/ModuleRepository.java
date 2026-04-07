package com.example.blog_web.repositories;

import com.example.blog_web.models.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
}
