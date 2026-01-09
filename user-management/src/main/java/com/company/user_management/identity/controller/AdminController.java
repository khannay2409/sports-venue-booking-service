package com.company.user_management.identity.controller;

import com.company.user_management.common.constants.Constants;
import com.company.user_management.identity.dto.response.AdminStatsResponse;
import com.company.user_management.common.response.ApiResponse;
import com.company.user_management.identity.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<AdminStatsResponse>> getStats() {

        AdminStatsResponse stats = adminService.getStats();
        return ResponseEntity.ok(
                ApiResponse.<AdminStatsResponse>builder()
                        .status(Constants.STATUS_SUCCESS)
                        .message("Admin stats fetched successfully")
                        .data(stats)
                        .build()
        );
    }
}
