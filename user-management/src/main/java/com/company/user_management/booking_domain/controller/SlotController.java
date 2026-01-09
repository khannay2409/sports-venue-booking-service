package com.company.user_management.booking_domain.controller;

import com.company.user_management.booking_domain.dto.request.CreateSlotRequest;
import com.company.user_management.booking_domain.dto.response.SlotResponse;
import com.company.user_management.booking_domain.dto.response.VenueResponse;
import com.company.user_management.booking_domain.service.SlotService;
import com.company.user_management.common.constants.Constants;
import com.company.user_management.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues/{venueId}/slots")
@RequiredArgsConstructor
@Tag(name = "Slots", description = "Add new slots and get details")
public class SlotController {

    private final SlotService slotService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> addSlot(
            @PathVariable Long venueId,
            @RequestBody CreateSlotRequest request
    ) {
        slotService.addSlot(venueId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .status(Constants.STATUS_SUCCESS)
                        .message("Slot created successfully")
                        .data(null)
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SlotResponse>>> getSlotsForVenue(
            @PathVariable Long venueId
    ) {
        List<SlotResponse> slots = slotService.getSlotsForVenue(venueId);
        return ResponseEntity.ok(
                ApiResponse.<List<SlotResponse>>builder()
                        .status(Constants.STATUS_SUCCESS)
                        .message("Slots fetched successfully")
                        .data(slots)
                        .build());
    }
}
