package com.sreyah.skybookai.repository;

import com.sreyah.skybookai.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long>
{
}