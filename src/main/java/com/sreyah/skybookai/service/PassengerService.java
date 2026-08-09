package com.sreyah.skybookai.service;

import com.sreyah.skybookai.entity.Passenger;

import java.util.List;
import java.util.Optional;
import com.sreyah.skybookai.repository.PassengerRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

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
    @GetMapping("/passengers")
    public List<Passenger> getAllPassengers()
    {
        return passengerRepository.findAll();
    }
    public Passenger updatePassenger(Long id, Passenger passenger)
    {
        Optional<Passenger> existingPassenger = passengerRepository.findById(id);
        Passenger existing = existingPassenger.get();
        existing.setName(passenger.getName());
        existing.setEmail(passenger.getEmail());
        existing.setPassword(passenger.getPassword());
        existing.setPhoneNumber(passenger.getPhoneNumber());
        existing.setDateOfBirth(passenger.getDateOfBirth());
        existing.setIdProofType(passenger.getIdProofType());
        existing.setIdProofNumber(passenger.getIdProofNumber());
        return passengerRepository.save(existing);
    }
    public void deletePassenger(Long id)
    {
        passengerRepository.deleteById(id);
    }
}