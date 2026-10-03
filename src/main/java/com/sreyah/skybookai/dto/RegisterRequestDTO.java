package com.sreyah.skybookai.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class RegisterRequestDTO
{
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(
            min = 6,
            message = "Password must contain at least 6 characters"
    )
    private String password;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(
            regexp = "\\d{10}",
            message = "Phone number must contain exactly 10 digits"
    )
    private String phoneNumber;

    private LocalDate dateOfBirth;

    @NotBlank(message = "ID proof type cannot be empty")
    private String idProofType;

    @NotBlank(message = "ID proof number cannot be empty")
    private String idProofNumber;

    public RegisterRequestDTO()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdProofType()
    {
        return idProofType;
    }

    public void setIdProofType(String idProofType)
    {
        this.idProofType = idProofType;
    }

    public String getIdProofNumber()
    {
        return idProofNumber;
    }

    public void setIdProofNumber(String idProofNumber)
    {
        this.idProofNumber = idProofNumber;
    }
}