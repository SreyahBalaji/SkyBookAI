package com.sreyah.skybookai.controller;

import com.sreyah.skybookai.entity.Booking;
import com.sreyah.skybookai.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingController
{
    @Autowired
    private BookingService bookingService;

    @PostMapping("/bookings")
    public ResponseEntity<Booking> createBooking(
            @RequestParam Long passengerId,
            @RequestParam Long flightId,
            @RequestParam String seatNumber)
    {
        Booking booking =
                bookingService.createBooking(
                        passengerId,
                        flightId,
                        seatNumber
                );

        return new ResponseEntity<>(
                booking,
                HttpStatus.CREATED
        );
    }
}