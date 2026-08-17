package com.sreyah.skybookai.controller;

import com.sreyah.skybookai.entity.Passenger;
import com.sreyah.skybookai.service.PassengerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @PostMapping("/passengers")
    public ResponseEntity<Passenger> savePassenger(@Valid @RequestBody Passenger passenger) {
        Passenger savedPassenger = passengerService.savePassenger(passenger);

        return new ResponseEntity<>(savedPassenger, HttpStatus.CREATED);
    }

    @GetMapping("/passengers/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id)
    {
        Passenger passenger = passengerService.getPassengerById(id);

        return new ResponseEntity<>(passenger, HttpStatus.OK);
    }

    @GetMapping("/passengers")
    public ResponseEntity<List<Passenger>> getAllPassengers()
    {
        List<Passenger> passengers = passengerService.getAllPassengers();

        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }

    @PutMapping("/passengers/{id}")
    public ResponseEntity<Passenger> updatePassenger(
            @PathVariable Long id,
            @Valid @RequestBody Passenger passenger) {
        Passenger updatedPassenger = passengerService.updatePassenger(id, passenger);

        return new ResponseEntity<>(updatedPassenger, HttpStatus.OK);
    }

    @DeleteMapping("/passengers/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id)
    {
        passengerService.deletePassenger(id);

        return ResponseEntity.noContent().build();
    }
}