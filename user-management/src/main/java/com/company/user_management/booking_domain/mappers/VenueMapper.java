package com.company.user_management.booking_domain.mappers;

import com.company.user_management.booking_domain.dto.response.VenueResponse;
import com.company.user_management.booking_domain.entity.Venue;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VenueMapper {


    VenueResponse toVenueResponse(Venue venue);

    List<VenueResponse> toVenueResponseList(List<Venue> venues);
}
