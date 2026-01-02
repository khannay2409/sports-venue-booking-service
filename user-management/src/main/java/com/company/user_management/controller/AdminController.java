package com.company.user_management.controller;

import com.company.user_management.dto.response.AdminStatsResponse;
import com.company.user_management.entity.User;
import com.company.user_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stats")
    public AdminStatsResponse getStats() {
        long totalUsers = userRepository.count();

        var lastLogin = userRepository.findAll()
                .stream()
                .map(User::getLastLogin)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        return new AdminStatsResponse(totalUsers, lastLogin);
    }
}
