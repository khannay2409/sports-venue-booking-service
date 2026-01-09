package com.company.user_management.identity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class UserEvent {

    private String eventType;
    private Long userId;
    private String email;
    private Instant timestamp;
}
