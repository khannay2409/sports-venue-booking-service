package com.company.user_management.identity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AdminStatsResponse {

    private long totalUsers;
    private LocalDateTime lastUserLogin;

}
