package com.company.user_management.booking_domain.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateSlotRequest {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
