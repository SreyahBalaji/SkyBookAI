package com.sreyah.skybookai.service;

import com.sreyah.skybookai.dto.AIResponseDTO;
import com.sreyah.skybookai.entity.Flight;
import com.sreyah.skybookai.repository.FlightRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class AIAssistantService
{
    @Autowired
    private FlightRepository flightRepository;

    public AIResponseDTO processMessage(String userMessage)
    {
        String message =
                userMessage.trim().toLowerCase();

        if (message.contains("how")
                && message.contains("book"))
        {
            return new AIResponseDTO(
                    "To book a flight, search for your route, "
                            + "select a flight and available seat, "
                            + "then confirm your booking. "
                            + "You must be logged in to book a flight."
            );
        }

        if (message.contains("cancel"))
        {
            return new AIResponseDTO(
                    "Open My Trips, select your confirmed booking "
                            + "and choose Cancel Booking. "
                            + "After cancellation, the seat becomes "
                            + "available again."
            );
        }

        if (message.contains("my booking")
                || message.contains("my bookings")
                || message.contains("my trips"))
        {
            return new AIResponseDTO(
                    "You can view all your bookings in My Trips "
                            + "after logging in."
            );
        }

        if (message.contains("cheapest"))
        {
            return findCheapestFlight(message);
        }

        if (message.contains("flight")
                || message.contains("flights"))
        {
            return findFlights(message);
        }

        if (message.contains("hello")
                || message.contains("hi")
                || message.contains("hey"))
        {
            return new AIResponseDTO(
                    "Hello! I am the SkyBookAI assistant. "
                            + "I can help you search for flights, "
                            + "find cheaper options, understand bookings "
                            + "and explain cancellations."
            );
        }

        if (message.contains("help"))
        {
            return new AIResponseDTO(
                    "I can help you search for flights, "
                            + "find the cheapest flight, "
                            + "understand how to book a ticket, "
                            + "view your trips or cancel a booking."
            );
        }

        return new AIResponseDTO(
                "I couldn't understand that request yet. "
                        + "Try asking about flights, cheapest flights, "
                        + "booking, My Trips or cancellation."
        );
    }

    private AIResponseDTO findCheapestFlight(
            String message)
    {
        List<Flight> flights =
                flightRepository.findAll();

        List<Flight> matchingFlights =
                flights.stream()
                        .filter(flight ->
                                routeMatches(
                                        message,
                                        flight
                                )
                        )
                        .filter(flight ->
                                flight.getAvailableSeats() > 0
                        )
                        .toList();

        if (matchingFlights.isEmpty())
        {
            return new AIResponseDTO(
                    "I couldn't find an available flight "
                            + "for that route."
            );
        }

        Flight cheapest =
                matchingFlights.stream()
                        .min(
                                Comparator.comparingDouble(
                                        Flight::getPrice
                                )
                        )
                        .orElseThrow();

        return new AIResponseDTO(
                "The cheapest available option is "
                        + cheapest.getAirline()
                        + " "
                        + cheapest.getFlightNumber()
                        + " from "
                        + cheapest.getSource()
                        + " to "
                        + cheapest.getDestination()
                        + " for ₹"
                        + cheapest.getPrice()
                        + ". Departure: "
                        + cheapest.getDepartureTime()
                        + "."
        );
    }

    private AIResponseDTO findFlights(
            String message)
    {
        List<Flight> flights =
                flightRepository.findAll();

        List<Flight> matchingFlights =
                flights.stream()
                        .filter(flight ->
                                routeMatches(
                                        message,
                                        flight
                                )
                        )
                        .filter(flight ->
                                flight.getAvailableSeats() > 0
                        )
                        .limit(5)
                        .toList();

        if (matchingFlights.isEmpty())
        {
            return new AIResponseDTO(
                    "I couldn't find available flights "
                            + "matching that route."
            );
        }

        StringBuilder response =
                new StringBuilder(
                        "I found these available flights:\n"
                );

        for (Flight flight : matchingFlights)
        {
            response.append("\n")
                    .append(flight.getAirline())
                    .append(" ")
                    .append(flight.getFlightNumber())
                    .append(" | ")
                    .append(flight.getSource())
                    .append(" → ")
                    .append(flight.getDestination())
                    .append(" | ₹")
                    .append(flight.getPrice())
                    .append(" | ")
                    .append(flight.getAvailableSeats())
                    .append(" seats available");
        }

        return new AIResponseDTO(
                response.toString()
        );
    }

    private boolean routeMatches(
            String message,
            Flight flight)
    {
        String source =
                flight.getSource().toLowerCase();

        String destination =
                flight.getDestination().toLowerCase();

        return message.contains(source)
                && message.contains(destination);
    }
}