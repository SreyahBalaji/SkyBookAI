package com.sreyah.skybookai.dto;

public class LoginResponseDTO
{
    private Long passengerId;
    private String name;
    private String email;
    private String token;
    private String message;

    public LoginResponseDTO(
            Long passengerId,
            String name,
            String email,
            String token,
            String message)
    {
        this.passengerId = passengerId;
        this.name = name;
        this.email = email;
        this.token = token;
        this.message = message;
    }

    public Long getPassengerId()
    {
        return passengerId;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getToken()
    {
        return token;
    }

    public String getMessage()
    {
        return message;
    }
}