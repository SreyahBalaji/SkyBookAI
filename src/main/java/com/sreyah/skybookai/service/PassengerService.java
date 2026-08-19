package com.sreyah.skybookai.service;

import com.sreyah.skybookai.entity.Passenger;

import java.util.List;
import java.util.Optional;

import com.sreyah.skybookai.exception.PassengerNotFoundException;
import com.sreyah.skybookai.repository.PassengerRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import com.sreyah.skybookai.dto.PassengerResponseDTO;

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
        return passengerRepository.findById(id).orElseThrow(() -> new PassengerNotFoundException("Passenger not found with id: " + id));
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
        if (!passengerRepository.existsById(id))
        {
            throw new PassengerNotFoundException(
                    "Passenger not found with id: " + id);
        }

        passengerRepository.deleteById(id);
    }
    public PassengerResponseDTO getPassengerResponseById(Long id)
    {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(
                        "Passenger not found with id: " + id));

        return new PassengerResponseDTO(
                passenger.getId(),
                passenger.getName(),
                passenger.getEmail(),
                passenger.getPhoneNumber(),
                passenger.getIdProofType()
        );
    }
    public List<PassengerResponseDTO> getAllPassengerResponses()
    {
        List<Passenger> passengers = passengerRepository.findAll();

        return passengers.stream()
                .map(passenger -> new PassengerResponseDTO(
                        passenger.getId(),
                        passenger.getName(),
                        passenger.getEmail(),
                        passenger.getPhoneNumber(),
                        passenger.getIdProofType()
                ))
                .toList();
    }
}