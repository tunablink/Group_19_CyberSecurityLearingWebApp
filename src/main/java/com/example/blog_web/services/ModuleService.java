package com.example.blog_web.services;

import com.example.blog_web.models.Module;
import com.example.blog_web.repositories.ModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Service đảm nhiệm việc lấy dữ liệu của các bài học (Module).
 * <p>
 * Gọi trực tiếp xuống tầng Repository để truy vấn danh sách toàn bộ module hoặc một module cụ thể.
 * </p>
 */
@Service
public class ModuleService {
    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    public Module getModuleById(Long id) {
        return moduleRepository.findAll().stream()
                .filter(module -> id.equals(module.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Module not found"));
    }
}
