package com.company.user_management.booking_domain.controller;

import com.company.user_management.booking_domain.dto.request.CreateBookingRequest;
import com.company.user_management.booking_domain.dto.response.SlotResponse;
import com.company.user_management.booking_domain.service.BookingService;
import com.company.user_management.common.constants.Constants;
import com.company.user_management.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Tag(name = "Bookings", description = "Slot booking and cancellation APIs")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> bookSlot(@RequestBody CreateBookingRequest request) {

        Long bookingId = bookingService.bookSlot(request);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .status(Constants.STATUS_SUCCESS)
                        .message("Booking successfully")
                        .data("Booking Id: " +bookingId)
                        .build());
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelBooking(
            @PathVariable Long id
    ) {
        bookingService.cancelBooking(id);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .status(Constants.STATUS_SUCCESS)
                        .message("Booking Cancelled successfully")
                        .data(null)
                        .build());
    }
}
