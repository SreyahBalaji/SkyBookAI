package com.sreyah.skybookai.dto;

public class PassengerResponseDTO
{
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String idProofType;

    public PassengerResponseDTO()
    {
    }

    public PassengerResponseDTO(Long id, String name, String email,
                                String phoneNumber, String idProofType)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.idProofType = idProofType;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getIdProofType()
    {
        return idProofType;
    }

    public void setIdProofType(String idProofType)
    {
        this.idProofType = idProofType;
    }
}