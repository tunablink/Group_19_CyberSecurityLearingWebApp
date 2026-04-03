package com.example.blog_web.repositories;

import com.example.blog_web.models.Module;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DashboardRepository {
    private final ModuleRepository moduleRepository;

    public DashboardRepository(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> findLearningPathByUsername(String username) {
        // Reuse module source to avoid data drift between /api/modules and dashboard API.
        return moduleRepository.findAll();
    }
}
