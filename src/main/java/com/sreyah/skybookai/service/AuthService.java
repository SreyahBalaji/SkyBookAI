package com.sreyah.skybookai.service;

import com.sreyah.skybookai.dto.LoginRequestDTO;
import com.sreyah.skybookai.dto.LoginResponseDTO;
import com.sreyah.skybookai.dto.RegisterRequestDTO;
import com.sreyah.skybookai.dto.RegisterResponseDTO;

import com.sreyah.skybookai.entity.Passenger;
import com.sreyah.skybookai.repository.PassengerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService
{
    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public RegisterResponseDTO register(
            RegisterRequestDTO request)
    {
        String normalizedEmail =
                request.getEmail()
                        .trim()
                        .toLowerCase();

        if (passengerRepository
                .existsByEmailIgnoreCase(normalizedEmail))
        {
            throw new IllegalArgumentException(
                    "Email is already registered"
            );
        }

        Passenger passenger =
                new Passenger();

        passenger.setName(
                request.getName()
        );

        passenger.setEmail(
                normalizedEmail
        );

        passenger.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        passenger.setPhoneNumber(
                request.getPhoneNumber()
        );

        passenger.setDateOfBirth(
                request.getDateOfBirth()
        );

        passenger.setIdProofType(
                request.getIdProofType()
        );

        passenger.setIdProofNumber(
                request.getIdProofNumber()
        );

        Passenger savedPassenger =
                passengerRepository.save(passenger);

        return new RegisterResponseDTO(
                savedPassenger.getId(),
                savedPassenger.getName(),
                savedPassenger.getEmail(),
                "Registration successful"
        );
    }

    public LoginResponseDTO login(
            LoginRequestDTO request)
    {
        String normalizedEmail =
                request.getEmail()
                        .trim()
                        .toLowerCase();

        Passenger passenger =
                passengerRepository
                        .findByEmailIgnoreCase(
                                normalizedEmail
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Invalid email or password"
                                )
                        );

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        passenger.getPassword()
                );

        if (!passwordMatches)
        {
            throw new IllegalArgumentException(
                    "Invalid email or password"
            );
        }

        String token =
                jwtService.generateToken(
                        passenger.getEmail(),
                        passenger.getId()
                );

        return new LoginResponseDTO(
                passenger.getId(),
                passenger.getName(),
                passenger.getEmail(),
                token,
                "Login successful"
        );
    }
}