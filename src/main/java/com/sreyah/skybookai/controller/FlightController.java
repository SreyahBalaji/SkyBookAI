package com.sreyah.skybookai.controller;

import com.sreyah.skybookai.dto.FlightResponseDTO;
import com.sreyah.skybookai.entity.Flight;
import com.sreyah.skybookai.service.FlightService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class FlightController
{
    @Autowired
    private FlightService flightService;

    @PostMapping("/flights")
    public ResponseEntity<Flight> saveFlight(
            @Valid @RequestBody Flight flight)
    {
        Flight savedFlight = flightService.saveFlight(flight);

        return new ResponseEntity<>(savedFlight, HttpStatus.CREATED);
    }
    @GetMapping("/flights/{id}")
    public ResponseEntity<FlightResponseDTO> getFlightById(
            @PathVariable Long id)
    {
        FlightResponseDTO flight =
                flightService.getFlightResponseById(id);

        return new ResponseEntity<>(flight, HttpStatus.OK);
    }
    @GetMapping("/flights")
    public ResponseEntity<List<Flight>> getAllFlights()
    {
        List<Flight> flights = flightService.getAllFlights();

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    @GetMapping("/flights/search")
    public ResponseEntity<List<FlightResponseDTO>> searchFlights(
            @RequestParam String source,
            @RequestParam String destination)
    {
        List<FlightResponseDTO> flights =
                flightService.searchFlights(source, destination);

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    @GetMapping("/flights/search/date")
    public ResponseEntity<List<FlightResponseDTO>> searchFlightsByDate(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam LocalDate date)
    {
        List<FlightResponseDTO> flights =
                flightService.searchFlightsByDate(
                        source,
                        destination,
                        date
                );

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    @GetMapping("/flights/search/price")
    public ResponseEntity<List<FlightResponseDTO>> searchFlightsByPrice(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam double maxPrice)
    {
        List<FlightResponseDTO> flights =
                flightService.searchFlightsByPrice(
                        source,
                        destination,
                        maxPrice
                );

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    @GetMapping("/flights/sorted/price")
    public ResponseEntity<List<FlightResponseDTO>> getFlightsSortedByPrice()
    {
        List<FlightResponseDTO> flights =
                flightService.getFlightsSortedByPrice();

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    @PutMapping("/flights/{id}")
    public ResponseEntity<Flight> updateFlight(
            @PathVariable Long id,
            @Valid @RequestBody Flight flight)
    {
        Flight updatedFlight =
                flightService.updateFlight(id, flight);

        return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
    }
    @DeleteMapping("/flights/{id}")
    public ResponseEntity<Void> deleteFlight(
            @PathVariable Long id)
    {
        flightService.deleteFlight(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}