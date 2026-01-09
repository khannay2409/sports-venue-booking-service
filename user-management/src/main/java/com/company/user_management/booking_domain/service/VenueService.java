package com.company.user_management.booking_domain.service;

import com.company.user_management.booking_domain.dto.request.CreateVenueRequest;
import com.company.user_management.booking_domain.dto.response.VenueResponse;
import com.company.user_management.booking_domain.entity.Venue;
import com.company.user_management.booking_domain.mappers.VenueMapper;
import com.company.user_management.booking_domain.repository.SlotRepository;
import com.company.user_management.booking_domain.repository.VenueRepository;
import com.company.user_management.common.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;

    private final SlotRepository slotRepository;

    private final VenueMapper venueMapper;

    public void createVenue(CreateVenueRequest request) {

        Venue venue = Venue.builder()
                .name(request.getName())
                .location(request.getLocation())
                .sportCode(request.getSportCode())
                .build();

        venueRepository.save(venue);
    }

    public List<VenueResponse> getAllVenues() {

        return venueMapper.toVenueResponseList(venueRepository.findAll());
    }

    public List<VenueResponse> getAvailableVenues(String sportCode, LocalDateTime start, LocalDateTime end) {

        if (!start.isBefore(end)) {
            throw new BadRequestException("Start time must be before end time");
        }

        List<Venue> venues = venueRepository.findBySportCode(sportCode);

        return venueMapper.toVenueResponseList(
                venues.stream()
                        .filter(venue ->
                                !slotRepository.existsBookedSlotOverlapping(
                                        venue, start, end
                                )
                        )
                        .toList()
        );
    }
}
