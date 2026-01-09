package com.company.user_management.booking_domain.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVenueRequest {

    private String name;
    private String location;
    private String sportCode;
}
