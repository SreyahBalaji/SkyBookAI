package com.sreyah.skybookai.service;

import com.sreyah.skybookai.entity.Booking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class BrevoEmailService
{
    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    @Value("${brevo.sender.name}")
    private String senderName;

    private final RestTemplate restTemplate =
            new RestTemplate();

    public void sendBookingConfirmation(Booking booking)
    {
        String url =
                "https://api.brevo.com/v3/smtp/email";

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        headers.set(
                "api-key",
                apiKey
        );

        Map<String, Object> sender =
                new HashMap<>();

        sender.put("name", senderName);
        sender.put("email", senderEmail);

        Map<String, Object> recipient =
                new HashMap<>();

        recipient.put(
                "email",
                booking.getPassenger().getEmail()
        );

        recipient.put(
                "name",
                booking.getPassenger().getName()
        );

        Map<String, Object> body =
                new HashMap<>();

        body.put("sender", sender);

        body.put(
                "to",
                List.of(recipient)
        );

        body.put(
                "subject",
                "SkyBookAI - Booking Confirmed"
        );

        body.put(
                "htmlContent",
                createEmailContent(booking)
        );

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(
                        body,
                        headers
                );

        restTemplate.postForEntity(
                url,
                request,
                String.class
        );
    }

    private String createEmailContent(
            Booking booking)
    {
        return """
                <html>
                <body>

                <h2>✈ SkyBookAI</h2>

                <h3>Booking Confirmed!</h3>

                <p>Hello %s,</p>

                <p>Your flight booking has been confirmed successfully.</p>

                <hr>

                <p><b>Booking Reference:</b> %s</p>

                <p><b>Flight:</b> %s</p>

                <p><b>Airline:</b> %s</p>

                <p><b>From:</b> %s</p>

                <p><b>To:</b> %s</p>


                <p><b>Departure:</b> %s</p>

                <p><b>Seat:</b> %s</p>

                <p><b>Status:</b> %s</p>

                <hr>

                <p>Thank you for choosing SkyBookAI.</p>

                </body>
                </html>
                """.formatted(
                booking.getPassenger().getName(),
                booking.getBookingReference(),
                booking.getFlight().getFlightNumber(),
                booking.getFlight().getAirline(),
                booking.getFlight().getSource(),
                booking.getFlight().getDestination(),
                booking.getFlight().getDepartureTime(),
                booking.getSeatNumber(),
                booking.getBookingStatus()
        );
    }

}