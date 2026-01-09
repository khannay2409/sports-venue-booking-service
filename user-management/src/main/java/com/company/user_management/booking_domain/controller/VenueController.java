package com.company.user_management.booking_domain.controller;

import com.company.user_management.booking_domain.dto.request.CreateVenueRequest;
import com.company.user_management.booking_domain.dto.response.VenueResponse;
import com.company.user_management.booking_domain.service.VenueService;
import com.company.user_management.common.constants.Constants;
import com.company.user_management.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/venues")
@RequiredArgsConstructor
@Tag(name = "Venues", description = "Venue and availability APIs")
public class VenueController {

    private final VenueService venueService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createVenue(
            @RequestBody CreateVenueRequest request
    ) {
        venueService.createVenue(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .status(Constants.STATUS_SUCCESS)
                        .message("Venue created successfully")
                        .data(null)
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VenueResponse>>> getAllVenues() {
        List<VenueResponse> venues = venueService.getAllVenues();
        return ResponseEntity.ok(
                ApiResponse.<List<VenueResponse>>builder()
                        .status(Constants.STATUS_SUCCESS)
                        .message("Venues fetched successfully")
                        .data(venues)
                        .build()
        );
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<VenueResponse>>> getAvailableVenues(
            @RequestParam String sportCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endTime
    ) {
        List<VenueResponse> venues =
                venueService.getAvailableVenues(sportCode, startTime, endTime);

        String message;
        if (venues.isEmpty()) {
            message = "No venue available for the selected time range";
        } else {
            message = "Venues fetched successfully";
        }

        return ResponseEntity.ok(
                ApiResponse.<List<VenueResponse>>builder()
                        .status(Constants.STATUS_SUCCESS)
                        .message(message)
                        .data(venues)
                        .build()
        );
    }
}
