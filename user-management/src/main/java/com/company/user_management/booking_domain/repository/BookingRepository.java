package com.company.user_management.booking_domain.repository;

import com.company.user_management.booking_domain.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByIdAndUserId(Long id, Long userId);

    boolean existsBySlotId(Long slotId);
}
