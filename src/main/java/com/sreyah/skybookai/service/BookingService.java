package com.sreyah.skybookai.service;

import com.sreyah.skybookai.dto.BookingResponseDTO;

import com.sreyah.skybookai.entity.Booking;
import com.sreyah.skybookai.entity.Flight;
import com.sreyah.skybookai.entity.Passenger;

import com.sreyah.skybookai.exception.BookingNotFoundException;

import com.sreyah.skybookai.repository.BookingRepository;
import com.sreyah.skybookai.repository.PassengerRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService
{
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private FlightService flightService;

    @Autowired
    private NotificationService notificationService;

    private Passenger getLoggedInPassenger(String email)
    {
        return passengerRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Logged-in passenger not found"
                        )
                );
    }

    @Transactional
    public BookingResponseDTO createBooking(
            String email,
            Long flightId,
            String seatNumber)
    {
        Passenger passenger =
                getLoggedInPassenger(email);

        Flight flight =
                flightService.getFlightById(flightId);

        String normalizedSeat =
                seatNumber.trim().toUpperCase();

        boolean seatAlreadyBooked =
                bookingRepository
                        .existsByFlightIdAndSeatNumberAndBookingStatus(
                                flightId,
                                normalizedSeat,
                                "CONFIRMED"
                        );

        if (seatAlreadyBooked)
        {
            throw new IllegalArgumentException(
                    "Seat " + normalizedSeat
                            + " is already booked"
            );
        }

        if (flight.getAvailableSeats() <= 0)
        {
            throw new IllegalArgumentException(
                    "No seats available for this flight"
            );
        }

        Booking booking = new Booking();

        booking.setPassenger(passenger);
        booking.setFlight(flight);
        booking.setSeatNumber(normalizedSeat);

        booking.setBookingReference(
                "SBK-" +
                        UUID.randomUUID()
                                .toString()
                                .substring(0, 6)
                                .toUpperCase()
        );

        booking.setBookingTime(
                LocalDateTime.now()
        );

        booking.setBookingStatus(
                "CONFIRMED"
        );

        flight.setAvailableSeats(
                flight.getAvailableSeats() - 1
        );

        flightService.saveExistingFlight(flight);

        Booking savedBooking =
                bookingRepository.save(booking);

        notificationService
                .sendBookingConfirmation(savedBooking);

        return convertToDTO(savedBooking);
    }

    public BookingResponseDTO getBookingById(
            Long id,
            String email)
    {
        Booking booking =
                getOwnedBooking(id, email);

        return convertToDTO(booking);
    }

    public BookingResponseDTO getBookingByReference(
            String reference,
            String email)
    {
        Booking booking =
                bookingRepository
                        .findByBookingReference(reference)
                        .orElseThrow(() ->
                                new BookingNotFoundException(
                                        "Booking not found: "
                                                + reference
                                )
                        );

        checkOwnership(
                booking,
                email
        );

        return convertToDTO(booking);
    }

    public List<BookingResponseDTO>
    getBookingsByPassenger(String email)
    {
        Passenger passenger =
                getLoggedInPassenger(email);

        return bookingRepository
                .findByPassengerId(
                        passenger.getId()
                )
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<String> getBookedSeats(Long flightId)
    {
        flightService.getFlightById(flightId);

        return bookingRepository
                .findByFlightIdAndBookingStatus(
                        flightId,
                        "CONFIRMED"
                )
                .stream()
                .map(Booking::getSeatNumber)
                .toList();
    }

    @Transactional
    public BookingResponseDTO cancelBooking(
            Long bookingId,
            String email)
    {
        Booking booking =
                getOwnedBooking(
                        bookingId,
                        email
                );

        if ("CANCELLED".equals(
                booking.getBookingStatus()))
        {
            throw new IllegalArgumentException(
                    "Booking is already cancelled"
            );
        }

        booking.setBookingStatus(
                "CANCELLED"
        );

        Flight flight =
                booking.getFlight();

        if (flight.getAvailableSeats()
                < flight.getTotalSeats())
        {
            flight.setAvailableSeats(
                    flight.getAvailableSeats() + 1
            );
        }

        flightService.saveExistingFlight(flight);

        Booking updatedBooking =
                bookingRepository.save(booking);

        return convertToDTO(updatedBooking);
    }

    private Booking getOwnedBooking(
            Long bookingId,
            String email)
    {
        Booking booking =
                bookingRepository
                        .findById(bookingId)
                        .orElseThrow(() ->
                                new BookingNotFoundException(
                                        "Booking not found with id: "
                                                + bookingId
                                )
                        );

        checkOwnership(
                booking,
                email
        );

        return booking;
    }

    private void checkOwnership(
            Booking booking,
            String email)
    {
        if (!booking
                .getPassenger()
                .getEmail()
                .equalsIgnoreCase(email))
        {
            throw new IllegalArgumentException(
                    "You are not allowed to access this booking"
            );
        }
    }

    private BookingResponseDTO convertToDTO(
            Booking booking)
    {
        return new BookingResponseDTO(
                booking.getId(),
                booking.getBookingReference(),
                booking.getPassenger().getName(),
                booking.getFlight().getFlightNumber(),
                booking.getFlight().getAirline(),
                booking.getFlight().getSource(),
                booking.getFlight().getDestination(),
                booking.getFlight().getDepartureTime(),
                booking.getFlight().getArrivalTime(),
                booking.getSeatNumber(),
                booking.getFlight().getPrice(),
                booking.getBookingTime(),
                booking.getBookingStatus()
        );
    }
}