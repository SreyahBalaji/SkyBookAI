package com.sreyah.skybookai.controller;

import com.sreyah.skybookai.entity.Flight;
import com.sreyah.skybookai.service.FlightService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FlightController
{
    @Autowired
    private FlightService flightService;

    @PostMapping("/flights")
    public ResponseEntity<Flight> saveFlight(@RequestBody Flight flight)
    {
        Flight savedFlight = flightService.saveFlight(flight);

        return new ResponseEntity<>(savedFlight, HttpStatus.CREATED);
    }
}