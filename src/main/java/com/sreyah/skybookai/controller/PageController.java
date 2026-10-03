package com.sreyah.skybookai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController
{
    @GetMapping("/")
    public String home()
    {
        return "index";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/register")
    public String register()
    {
        return "register";
    }

    @GetMapping("/search-flights")
    public String flights()
    {
        return "flights";
    }

    @GetMapping("/book-flight")
    public String booking()
    {
        return "booking";
    }

    @GetMapping("/my-trips")
    public String myTrips()
    {
        return "my-trips";
    }
}