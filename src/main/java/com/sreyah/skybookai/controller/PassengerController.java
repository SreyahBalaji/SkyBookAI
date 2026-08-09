package com.sreyah.skybookai.controller;

import com.sreyah.skybookai.entity.Passenger;
import com.sreyah.skybookai.service.PassengerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PassengerController {

    @Autowired
    private PassengerService passengerService;
    @PostMapping("/passengers")
    public Passenger savePassenger(@RequestBody Passenger passenger)
    {
        return passengerService.savePassenger(passenger);
    }
    @GetMapping("/passengers/{id}")
    public Passenger getPassengerById(@PathVariable Long id)
    {
        return passengerService.getPassengerById(id);
    }
    @GetMapping("/passengers")
    public List<Passenger> getAllPassengers()
    {
        return passengerService.getAllPassengers();
    }
    @PutMapping("/passengers/{id}")
    public Passenger updatePassenger(@PathVariable Long id, @RequestBody Passenger passenger)
    {
        return passengerService.updatePassenger(id, passenger);
    }
    @DeleteMapping("/passengers/{id}")
    public void deletePassenger(@PathVariable Long id)
    {
        passengerService.deletePassenger(id);
    }
}