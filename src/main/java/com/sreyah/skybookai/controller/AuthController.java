package com.sreyah.skybookai.controller;

import com.sreyah.skybookai.dto.LoginRequestDTO;
import com.sreyah.skybookai.dto.LoginResponseDTO;
import com.sreyah.skybookai.dto.RegisterRequestDTO;
import com.sreyah.skybookai.dto.RegisterResponseDTO;

import com.sreyah.skybookai.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO>
    register(
            @Valid @RequestBody
            RegisterRequestDTO request)
    {
        return new ResponseEntity<>(
                authService.register(request),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO>
    login(
            @Valid @RequestBody
            LoginRequestDTO request)
    {
        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}