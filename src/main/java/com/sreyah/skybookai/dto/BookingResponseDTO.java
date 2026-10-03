package com.sreyah.skybookai.dto;

import java.time.LocalDateTime;

public class BookingResponseDTO
{
    private Long id;
    private String bookingReference;
    private String passengerName;
    private String flightNumber;
    private String airline;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String seatNumber;
    private double price;
    private LocalDateTime bookingTime;
    private String bookingStatus;

    public BookingResponseDTO(
            Long id,
            String bookingReference,
            String passengerName,
            String flightNumber,
            String airline,
            String source,
            String destination,
            LocalDateTime departureTime,
            LocalDateTime arrivalTime,
            String seatNumber,
            double price,
            LocalDateTime bookingTime,
            String bookingStatus)
    {
        this.id = id;
        this.bookingReference = bookingReference;
        this.passengerName = passengerName;
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seatNumber = seatNumber;
        this.price = price;
        this.bookingTime = bookingTime;
        this.bookingStatus = bookingStatus;
    }

    public Long getId()
    {
        return id;
    }

    public String getBookingReference()
    {
        return bookingReference;
    }

    public String getPassengerName()
    {
        return passengerName;
    }

    public String getFlightNumber()
    {
        return flightNumber;
    }

    public String getAirline()
    {
        return airline;
    }

    public String getSource()
    {
        return source;
    }

    public String getDestination()
    {
        return destination;
    }

    public LocalDateTime getDepartureTime()
    {
        return departureTime;
    }

    public LocalDateTime getArrivalTime()
    {
        return arrivalTime;
    }

    public String getSeatNumber()
    {
        return seatNumber;
    }

    public double getPrice()
    {
        return price;
    }

    public LocalDateTime getBookingTime()
    {
        return bookingTime;
    }

    public String getBookingStatus()
    {
        return bookingStatus;
    }
}