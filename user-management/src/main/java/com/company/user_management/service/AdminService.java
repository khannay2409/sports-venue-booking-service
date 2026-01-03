package com.company.user_management.service;

import com.company.user_management.dto.response.AdminStatsResponse;
import com.company.user_management.entity.User;
import com.company.user_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

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
