package com.sreyah.skybookai.service;

import com.sreyah.skybookai.entity.Booking;
import com.sreyah.skybookai.entity.Flight;
import com.sreyah.skybookai.entity.Passenger;
import com.sreyah.skybookai.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BookingService
{
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private FlightService flightService;
    @Autowired
    private NotificationService notificationService;
    public Booking createBooking(
            Long passengerId,
            Long flightId,
            String seatNumber)
    {
        Passenger passenger =
                passengerService.getPassengerById(passengerId);

        Flight flight =
                flightService.getFlightById(flightId);

        Booking booking = new Booking();

        booking.setPassenger(passenger);
        booking.setFlight(flight);
        booking.setSeatNumber(seatNumber);

        booking.setBookingReference(
                "SBK-" +
                        UUID.randomUUID()
                                .toString()
                                .substring(0, 6)
                                .toUpperCase()
        );

        booking.setBookingTime(LocalDateTime.now());

        booking.setBookingStatus("CONFIRMED");

        Booking savedBooking =
                bookingRepository.save(booking);

        notificationService.sendBookingConfirmation(
                savedBooking
        );

        return savedBooking;
    }
}