package com.company.user_management.booking_domain.service;

import com.company.user_management.booking_domain.dto.request.CreateSlotRequest;
import com.company.user_management.booking_domain.dto.response.SlotResponse;
import com.company.user_management.booking_domain.entity.Slot;
import com.company.user_management.booking_domain.entity.Venue;
import com.company.user_management.booking_domain.mappers.SlotMapper;
import com.company.user_management.booking_domain.repository.SlotRepository;
import com.company.user_management.booking_domain.repository.VenueRepository;
import com.company.user_management.common.enums.SlotStatus;
import com.company.user_management.common.exceptions.BadRequestException;
import com.company.user_management.common.exceptions.SlotNotFoundException;
import com.company.user_management.common.exceptions.VenueNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SlotService {

    private final VenueRepository venueRepository;

    private final SlotRepository slotRepository;

    private final SlotMapper slotMapper;

    public void addSlot(Long venueId, CreateSlotRequest request) {

        if (request.getStartTime().isAfter(request.getEndTime()) ||
                request.getStartTime().isEqual(request.getEndTime())) {
            throw new BadRequestException("Start time must be before end time");
        }

        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new VenueNotFoundException("Venue not found with id: " + venueId));

        boolean overlapExists = slotRepository.existsOverlappingSlot(
                venue,
                request.getStartTime(),
                request.getEndTime()
        );

        if (overlapExists) {
            throw new BadRequestException("Slot overlaps with existing slot");
        }

        Slot slot = Slot.builder()
                .venue(venue)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status(SlotStatus.AVAILABLE)
                .build();

        slotRepository.save(slot);
    }

    public List<SlotResponse> getSlotsForVenue(Long venueId) {

        List<Slot> slots = slotRepository.findByVenueIdOrderByStartTimeAsc(venueId);

        if (slots.isEmpty()) {
            throw new SlotNotFoundException("No slots found for this venue");
        }

        return slotMapper.toSlotResponseList(slots);
    }

}
