package com.sreyah.skybookai.repository;

import com.sreyah.skybookai.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long>
{
    List<Flight> findBySourceIgnoreCaseAndDestinationIgnoreCase(
            String source,
            String destination
    );
    List<Flight> findBySourceIgnoreCaseAndDestinationIgnoreCaseAndDepartureTimeBetween(
            String source,
            String destination,
            LocalDateTime startOfDay,
            LocalDateTime endOfDay
    );
    List<Flight> findBySourceIgnoreCaseAndDestinationIgnoreCaseAndPriceLessThanEqual(
            String source,
            String destination,
            double maxPrice
    );
}
