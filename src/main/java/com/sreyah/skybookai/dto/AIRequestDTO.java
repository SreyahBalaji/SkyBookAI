package com.sreyah.skybookai.dto;

import jakarta.validation.constraints.NotBlank;

public class AIRequestDTO
{
    @NotBlank(message = "Message cannot be empty")
    private String message;

    public AIRequestDTO()
    {
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}