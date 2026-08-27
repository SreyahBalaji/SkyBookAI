package com.sreyah.skybookai.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Booking
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookingReference;

    private String seatNumber;

    private LocalDateTime bookingTime;

    private String bookingStatus;
    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
    public Booking()
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

    public String getBookingReference()
    {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference)
    {
        this.bookingReference = bookingReference;
    }

    public String getSeatNumber()
    {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber)
    {
        this.seatNumber = seatNumber;
    }

    public LocalDateTime getBookingTime()
    {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime)
    {
        this.bookingTime = bookingTime;
    }

    public String getBookingStatus()
    {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus)
    {
        this.bookingStatus = bookingStatus;
    }
    public Passenger getPassenger()
    {
        return passenger;
    }

    public void setPassenger(Passenger passenger)
    {
        this.passenger = passenger;
    }

    public Flight getFlight()
    {
        return flight;
    }

    public void setFlight(Flight flight)
    {
        this.flight = flight;
    }
}