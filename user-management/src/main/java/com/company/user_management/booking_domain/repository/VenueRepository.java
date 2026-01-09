package com.company.user_management.booking_domain.repository;

import com.company.user_management.booking_domain.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    List<Venue> findBySportCode(String sportsCode);
}
