package com.company.user_management.booking_domain.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Getter
@Setter
public class SlotResponse {

    private Long slotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
}
