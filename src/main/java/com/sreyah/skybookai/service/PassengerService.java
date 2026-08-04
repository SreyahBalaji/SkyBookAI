package com.sreyah.skybookai.service;

import com.sreyah.skybookai.entity.Passenger;
import com.sreyah.skybookai.repository.PassengerRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;
    public Passenger savePassenger(Passenger passenger){
        return passengerRepository.save(passenger);
    }
}