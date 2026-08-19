package com.sreyah.skybookai.repository;

import com.sreyah.skybookai.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long>
{
}