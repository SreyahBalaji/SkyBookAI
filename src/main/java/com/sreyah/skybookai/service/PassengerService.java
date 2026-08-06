package com.sreyah.skybookai.service;

import com.sreyah.skybookai.entity.Passenger;
import java.util.Optional;
import com.sreyah.skybookai.repository.PassengerRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PassengerService
{
    @Autowired
    private PassengerRepository passengerRepository;
    public Passenger savePassenger(Passenger passenger){
        return passengerRepository.save(passenger);
    }
    public Passenger getPassengerById(Long id)
    {
        Optional<Passenger> passenger = passengerRepository.findById(id);
        return passenger.get();
    }
}