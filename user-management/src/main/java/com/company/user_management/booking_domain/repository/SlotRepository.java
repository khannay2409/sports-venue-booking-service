package com.company.user_management.booking_domain.repository;

import com.company.user_management.booking_domain.entity.Slot;
import com.company.user_management.booking_domain.entity.Venue;
import com.company.user_management.common.enums.SlotStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Long> {

    /**
     * Used while booking to lock the slot row
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Slot s WHERE s.id = :slotId")
    Optional<Slot> findByIdForUpdate(Long slotId);

    /**
     * Check overlapping slots for same venue
     */
    @Query("""
        SELECT COUNT(s) > 0 FROM Slot s
        WHERE s.venue = :venue
        AND (:start < s.endTime AND :end > s.startTime)
    """)
    boolean existsOverlappingSlot(
            Venue venue,
            LocalDateTime start,
            LocalDateTime end
    );

    /**
     * Find slots in time range (availability)
     */
    List<Slot> findByVenueAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
            Venue venue,
            SlotStatus status,
            LocalDateTime end,
            LocalDateTime start
    );

    @Query("""
    SELECT COUNT(s) > 0
    FROM Slot s
    WHERE s.venue = :venue
      AND s.status = 'BOOKED'
      AND (:start < s.endTime AND :end > s.startTime)""")
    boolean existsBookedSlotOverlapping(
            Venue venue,
            LocalDateTime start,
            LocalDateTime end
    );

    List<Slot> findByVenueIdOrderByStartTimeAsc(Long venueId);
}
