package com.umaraliev.personapi.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IndividualDTO {
    private String firstName;
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;
    private String secretKey;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String passportNumber;
    private String phoneNumber;
}
