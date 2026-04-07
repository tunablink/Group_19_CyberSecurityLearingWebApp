package com.example.blog_web.controllers;

import com.example.blog_web.compete.service.TournamentPointService;
import com.example.blog_web.models.LabValidationRequestDto;
import com.example.blog_web.models.LabValidationResponseDto;
import com.example.blog_web.services.LabService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * REST API Controller thực thi tiến trình đánh giá Security Lab.
 * <p>
 * Cung cấp các endpoint cho phép người dùng submit các kỹ thuật thực nghiệm (ví dụ: exploit),
 * tự động kích hoạt bộ máy validation ngầm để chấm điểm kỹ thuật đó (Đúng/Sai).
 * </p>
 */
@RestController
@RequestMapping("/api/labs")
public class LabApiController {
    private final LabService labService;
    private final TournamentPointService tournamentPointService;

    public LabApiController(LabService labService, TournamentPointService tournamentPointService) {
        this.labService = labService;
        this.tournamentPointService = tournamentPointService;
    }

    @PostMapping("/validate")
    public ResponseEntity<LabValidationResponseDto> validateLab(@Valid @RequestBody LabValidationRequestDto request,
                                                                Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "Unauthorized");
        }

        // Validation strategy is selected at runtime by factory.
        LabValidationResponseDto response = labService.validateLab(request.getLabType(), request.getInput());
        if (response.isSuccess()) {
            tournamentPointService.recordAfterLabPass(authentication.getName(), request.getLabType());
        }
        return ResponseEntity.ok(response);
    }
}
