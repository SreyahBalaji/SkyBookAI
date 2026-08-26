package com.sreyah.skybookai.dto;

import java.time.LocalDateTime;

public class FlightResponseDTO
{
    private Long id;
    private String flightNumber;
    private String airline;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double price;
    private int availableSeats;

    public FlightResponseDTO()
    {
    }

    public FlightResponseDTO(
            Long id,
            String flightNumber,
            String airline,
            String source,
            String destination,
            LocalDateTime departureTime,
            LocalDateTime arrivalTime,
            double price,
            int availableSeats)
    {
        this.id = id;
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.availableSeats = availableSeats;
    }

    public Long getId()
    {
        return id;
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

    public double getPrice()
    {
        return price;
    }

    public int getAvailableSeats()
    {
        return availableSeats;
    }


}