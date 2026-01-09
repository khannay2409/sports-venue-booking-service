package com.company.user_management.booking_domain.service;

import com.company.user_management.booking_domain.dto.request.CreateBookingRequest;
import com.company.user_management.booking_domain.entity.Booking;
import com.company.user_management.booking_domain.entity.Slot;
import com.company.user_management.booking_domain.repository.BookingRepository;
import com.company.user_management.booking_domain.repository.SlotRepository;
import com.company.user_management.common.enums.BookingStatus;
import com.company.user_management.common.enums.SlotStatus;
import com.company.user_management.common.exceptions.BadRequestException;
import com.company.user_management.common.exceptions.SlotNotFoundException;
import com.company.user_management.identity.entity.User;
import com.company.user_management.identity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final SlotRepository slotRepository;

    private final BookingRepository bookingRepository;

    private final UserService userService;

    @Transactional
    public Long bookSlot(CreateBookingRequest request) {

        Slot slot = slotRepository.findByIdForUpdate(request.getSlotId())
                .orElseThrow(() -> new SlotNotFoundException("Slot not found"));

        // Check availability
            if (slot.getStatus() == SlotStatus.BOOKED) {
            throw new BadRequestException("Slot already booked");
        }

        User user = userService.getUser();

        // Create booking
        Booking booking = Booking.builder()
                .slot(slot)
                .user(user)
                .status(BookingStatus.CONFIRMED)
                .bookedAt(LocalDateTime.now())
                .build();

        bookingRepository.save(booking);

        slot.setStatus(SlotStatus.BOOKED);
        return booking.getId();
    }

    @Transactional
    public void cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BadRequestException("Booking not found"));

        User user = userService.getUser();

        if (!booking.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("You cannot cancel this booking");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException("Booking already cancelled");
        }

        // Cancel booking
        booking.setStatus(BookingStatus.CANCELLED);

        // Free slot
        Slot slot = booking.getSlot();
        slot.setStatus(SlotStatus.AVAILABLE);
    }
}
