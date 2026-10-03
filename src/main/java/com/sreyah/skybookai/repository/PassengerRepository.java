package com.sreyah.skybookai.repository;

import com.sreyah.skybookai.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository
        extends JpaRepository<Passenger, Long>
{
    Optional<Passenger> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}