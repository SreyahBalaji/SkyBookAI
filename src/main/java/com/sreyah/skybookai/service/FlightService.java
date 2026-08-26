package com.sreyah.skybookai.service;

import com.sreyah.skybookai.dto.FlightResponseDTO;
import com.sreyah.skybookai.entity.Flight;
import com.sreyah.skybookai.repository.FlightRepository;
import com.sreyah.skybookai.exception.FlightNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService
{
    @Autowired
    private FlightRepository flightRepository;

    public Flight saveFlight(Flight flight)
    {
        if (flight.getAvailableSeats() > flight.getTotalSeats())
        {
            throw new IllegalArgumentException(
                    "Available seats cannot be greater than total seats");
        }

        return flightRepository.save(flight);
    }
    public Flight getFlightById(Long id)
    {
        return flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException(
                        "Flight not found with id: " + id));
    }
    public List<Flight> getAllFlights()
    {
        return flightRepository.findAll();
    }
    public List<FlightResponseDTO> searchFlights(
            String source,
            String destination)
    {
        return flightRepository
                .findBySourceIgnoreCaseAndDestinationIgnoreCase(
                        source,
                        destination
                )
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
    private FlightResponseDTO convertToDTO(Flight flight)
    {
        return new FlightResponseDTO(
                flight.getId(),
                flight.getFlightNumber(),
                flight.getAirline(),
                flight.getSource(),
                flight.getDestination(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getPrice(),
                flight.getAvailableSeats()
        );
    }
    public FlightResponseDTO getFlightResponseById(Long id)
    {
        Flight flight = getFlightById(id);

        return convertToDTO(flight);
    }
    public List<FlightResponseDTO> searchFlightsByDate(
            String source,
            String destination,
            LocalDate date)
    {
        LocalDateTime startOfDay = date.atStartOfDay();

        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return flightRepository
                .findBySourceIgnoreCaseAndDestinationIgnoreCaseAndDepartureTimeBetween(
                        source,
                        destination,
                        startOfDay,
                        endOfDay
                )
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
    public List<FlightResponseDTO> searchFlightsByPrice(
            String source,
            String destination,
            double maxPrice)
    {
        return flightRepository
                .findBySourceIgnoreCaseAndDestinationIgnoreCaseAndPriceLessThanEqual(
                        source,
                        destination,
                        maxPrice
                )
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
    public List<FlightResponseDTO> getFlightsSortedByPrice()
    {
        return flightRepository
                .findAll(Sort.by(Sort.Direction.ASC, "price"))
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
    public Flight updateFlight(Long id, Flight flight)
    {
        if (flight.getAvailableSeats() > flight.getTotalSeats())
        {
            throw new IllegalArgumentException(
                    "Available seats cannot be greater than total seats");
        }
        Flight existingFlight = getFlightById(id);

        existingFlight.setFlightNumber(flight.getFlightNumber());
        existingFlight.setAirline(flight.getAirline());
        existingFlight.setSource(flight.getSource());
        existingFlight.setDestination(flight.getDestination());
        existingFlight.setDepartureTime(flight.getDepartureTime());
        existingFlight.setArrivalTime(flight.getArrivalTime());
        existingFlight.setPrice(flight.getPrice());
        existingFlight.setTotalSeats(flight.getTotalSeats());
        existingFlight.setAvailableSeats(flight.getAvailableSeats());

        return flightRepository.save(existingFlight);
    }
    public void deleteFlight(Long id)
    {
        Flight flight = getFlightById(id);

        flightRepository.delete(flight);
    }
}