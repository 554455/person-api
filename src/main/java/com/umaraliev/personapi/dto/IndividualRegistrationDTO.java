package com.umaraliev.personapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IndividualRegistrationDTO {
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;

    @NotBlank(message = "Secret key is mandatory")
    private String secretKey;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "State is mandatory")
    private String state;

    @NotBlank(message = "Zip code is mandatory")
    private String zipCode;

    @NotBlank(message = "Country is mandatory")
    private String country;

    @NotBlank(message = "Passport number is mandatory")
    private String passportNumber;

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;
}
