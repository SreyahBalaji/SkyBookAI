package com.sreyah.skybookai.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Entity
public class Flight
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Flight number cannot be empty")
    private String flightNumber;

    @NotBlank(message = "Airline cannot be empty")
    private String airline;

    @NotBlank(message = "Source cannot be empty")
    private String source;

    @NotBlank(message = "Destination cannot be empty")
    private String destination;
    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;
    @Positive(message = "Price must be greater than zero")
    private double price;

    @Positive(message = "Total seats must be greater than zero")
    private int totalSeats;

    @PositiveOrZero(message = "Available seats cannot be negative")
    private int availableSeats;
    public Flight()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFlightNumber()
    {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber)
    {
        this.flightNumber = flightNumber;
    }

    public String getAirline()
    {
        return airline;
    }

    public void setAirline(String airline)
    {
        this.airline = airline;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getDestination()
    {
        return destination;
    }

    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    public LocalDateTime getDepartureTime()
    {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime)
    {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime()
    {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public int getTotalSeats()
    {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats)
    {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats()
    {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats)
    {
        this.availableSeats = availableSeats;
    }
}