package com.sreyah.skybookai.service;

import com.sreyah.skybookai.entity.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService
{
    @Autowired
    private BrevoEmailService brevoEmailService;
    public void sendBookingConfirmation(
            Booking booking)
    {
        brevoEmailService
                .sendBookingConfirmation(booking);
    }
}