package com.sreyah.skybookai.service;

import com.sreyah.skybookai.entity.Booking;
import org.springframework.stereotype.Service;

@Service
public class NotificationService
{
    public void sendBookingConfirmation(Booking booking)
    {
        System.out.println(
                "Booking confirmation will be sent to: " +
                        booking.getPassenger().getEmail()
        );
    }
}