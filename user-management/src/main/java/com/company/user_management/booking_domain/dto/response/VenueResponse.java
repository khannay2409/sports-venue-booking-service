package com.company.user_management.booking_domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VenueResponse {

    private Long id;
    private String name;
    private String location;
    private String sportCode;
}
