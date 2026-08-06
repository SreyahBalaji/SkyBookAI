package com.sreyah.skybookai.controller;

import com.sreyah.skybookai.entity.Passenger;
import com.sreyah.skybookai.service.PassengerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}