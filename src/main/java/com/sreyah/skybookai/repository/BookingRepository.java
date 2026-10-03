package com.sreyah.skybookai.repository;

import com.sreyah.skybookai.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long>
{
    boolean existsByFlightIdAndSeatNumberAndBookingStatus(
            Long flightId,
            String seatNumber,
            String bookingStatus
    );

    Optional<Booking> findByBookingReference(
            String bookingReference
    );

    List<Booking> findByPassengerId(
            Long passengerId
    );

    List<Booking> findByFlightIdAndBookingStatus(
            Long flightId,
            String bookingStatus
    );
}