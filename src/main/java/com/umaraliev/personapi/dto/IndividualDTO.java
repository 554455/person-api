package com.umaraliev.personapi.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.javers.core.metamodel.annotation.ValueObject;

@Data
@ValueObject
public class IndividualDTO {
    private String passportNumber;
    private String phoneNumber;
    private UserDTO user;

    @Data
    public static class UserDTO {

        @NotBlank
        @Email
        private String email;
        private String secretKey;
        private String firstName;
        private String lastName;
        private AddressDTO address;
    }

    @Data
    public static class AddressDTO {
        private String address;
        private String city;
        private String state;
        private String zipCode;
        private CountryDTO country;
    }

    @Data
    public static class CountryDTO{
        private String name;
    }

}
