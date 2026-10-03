package com.sreyah.skybookai.controller;

import com.sreyah.skybookai.dto.BookingRequestDTO;
import com.sreyah.skybookai.dto.BookingResponseDTO;
import com.sreyah.skybookai.service.BookingService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController
{
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(
            @Valid @RequestBody BookingRequestDTO request,
            Authentication authentication)
    {
        BookingResponseDTO booking =
                bookingService.createBooking(
                        authentication.getName(),
                        request.getFlightId(),
                        request.getSeatNumber()
                );

        return new ResponseEntity<>(
                booking,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(
            @PathVariable Long id,
            Authentication authentication)
    {
        return ResponseEntity.ok(
                bookingService.getBookingById(
                        id,
                        authentication.getName()
                )
        );
    }

    @GetMapping("/reference/{reference}")
    public ResponseEntity<BookingResponseDTO>
    getBookingByReference(
            @PathVariable String reference,
            Authentication authentication)
    {
        return ResponseEntity.ok(
                bookingService.getBookingByReference(
                        reference,
                        authentication.getName()
                )
        );
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookingResponseDTO>>
    getMyBookings(Authentication authentication)
    {
        return ResponseEntity.ok(
                bookingService.getBookingsByPassenger(
                        authentication.getName()
                )
        );
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponseDTO> cancelBooking(
            @PathVariable Long id,
            Authentication authentication)
    {
        return ResponseEntity.ok(
                bookingService.cancelBooking(
                        id,
                        authentication.getName()
                )
        );
    }
}