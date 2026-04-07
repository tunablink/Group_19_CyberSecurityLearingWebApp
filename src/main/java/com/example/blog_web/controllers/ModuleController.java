package com.example.blog_web.controllers;

import com.example.blog_web.models.Module;
import com.example.blog_web.services.ModuleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller dùng để lấy các học phần (module).
 * <p>
 * Cung cấp các endpoint (chỉ đọc) để trả về danh sách khóa học hiện có và
 * metadata của chúng để client tải động.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping("/modules")
    public List<Module> getModules() {
        return moduleService.getAllModules();
    }

    @GetMapping("/modules/{id}")
    public Module getModuleById(@PathVariable Long id) {
        // UC-09: allow learners to open a specific module by id.
        return moduleService.getModuleById(id);
    }
}
