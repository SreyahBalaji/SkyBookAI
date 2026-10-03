package com.sreyah.skybookai.exception;

public class BookingNotFoundException extends RuntimeException
{
    public BookingNotFoundException(String message)
    {
        super(message);
    }
}