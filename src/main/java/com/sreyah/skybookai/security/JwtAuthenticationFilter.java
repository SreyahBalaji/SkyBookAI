package com.sreyah.skybookai.security;

import com.sreyah.skybookai.entity.Passenger;
import com.sreyah.skybookai.repository.PassengerRepository;
import com.sreyah.skybookai.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter
{
    @Autowired
    private JwtService jwtService;

    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException
    {
        String authorizationHeader =
                request.getHeader("Authorization");

        if (authorizationHeader == null
                ||
                !authorizationHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        String token =
                authorizationHeader.substring(7);

        try
        {
            String email =
                    jwtService.extractEmail(token);

            if (email != null
                    &&
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            == null)
            {
                Passenger passenger =
                        passengerRepository
                                .findByEmailIgnoreCase(email)
                                .orElse(null);

                if (passenger != null
                        &&
                        jwtService.isTokenValid(
                                token,
                                passenger.getEmail()
                        ))
                {
                    UsernamePasswordAuthenticationToken
                            authentication =
                            new UsernamePasswordAuthenticationToken(
                                    passenger.getEmail(),
                                    null,
                                    List.of(
                                            new SimpleGrantedAuthority(
                                                    "ROLE_PASSENGER"
                                            )
                                    )
                            );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(
                                    authentication
                            );
                }
            }
        }
        catch (Exception ignored)
        {
            // Invalid JWT.
            // SecurityConfig will reject access
            // if the endpoint requires authentication.
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}