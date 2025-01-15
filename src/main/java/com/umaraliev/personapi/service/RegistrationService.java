package com.umaraliev.personapi.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.umaraliev.personapi.dto.IndividualRegistrationDTO;
import com.umaraliev.personapi.dto.IndividualUpdateDTO;
import com.umaraliev.personapi.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final CountryService countryService;
    private final AddressService addressService;
    private final IndividualService individualService;
    private final UserHistoryService userHistoryService;

    @Transactional
    public User registrationUser(IndividualRegistrationDTO individualRegistrationDto) throws JsonProcessingException {

       try {
           User user = new User();
           user.setFirstName(individualRegistrationDto.getFirstName());
           user.setLastName(individualRegistrationDto.getLastName());
           user.setEmail(individualRegistrationDto.getEmail());
           user.setSecretKey(individualRegistrationDto.getSecretKey());
           user.setCreated(LocalDateTime.now());
           user.setUpdated(LocalDateTime.now());
           userService.saveUser(user);

           System.out.println(user.getId());


           Country country = new Country();
           country.setName(individualRegistrationDto.getCountry());
           country.setCreated(LocalDateTime.now());
           country.setUpdated(LocalDateTime.now());
           countryService.saveCountry(country);

           Address address = new Address();
           address.setAddress(individualRegistrationDto.getAddress());
           address.setCity(individualRegistrationDto.getCity());
           address.setState(individualRegistrationDto.getState());
           address.setZipCode(individualRegistrationDto.getZipCode());
           address.setCreated(LocalDateTime.now());
           address.setUpdated(LocalDateTime.now());
           address.setArchived(LocalDateTime.now());
           address.setCountry(country);
           addressService.saveAddress(address);
           user.setAddress(address);
           userService.saveUser(user);

           Individual individual = new Individual();
           individual.setPassportNumber(individualRegistrationDto.getPassportNumber());
           individual.setPhoneNumber(individualRegistrationDto.getPhoneNumber());
           individual.setEmail(individualRegistrationDto.getEmail());
           individual.setVerifiedAt(LocalDateTime.now());

           individual.setArchivedAt(LocalDateTime.now());
           individual.setStatus("ACTIVE");
           individual.setUser(user);
           individualService.saveIndividual(individual);


           UserHistory userHistory = new UserHistory();
           userHistory.setUser(user);
           userHistory.setUserType("INDIVIDUAL");
           userHistory.setReason("REGISTRATION");
           userHistory.setCreated(LocalDateTime.now());
           userHistoryService.saveUserHistory(userHistory);
           userService.saveUser(user);
           return user;
       } catch (Exception e) {
           throw new RuntimeException("Failed to register user: " + e.getMessage());
       }
    }

    @Transactional
    public void updateUser(UUID id, IndividualUpdateDTO individualUpdateDto) {

        Optional<User> optionalUser = userService.getUserById(id);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found with id: " + id);
        }

        User user = optionalUser.get();

        if (individualUpdateDto.getFirstName() != null) {
            user.setFirstName(individualUpdateDto.getFirstName());
        }
        if (individualUpdateDto.getLastName() != null) {
            user.setLastName(individualUpdateDto.getLastName());
        }
        if (individualUpdateDto.getEmail() != null) {
            user.setEmail(individualUpdateDto.getEmail());
        }
        if (individualUpdateDto.getSecretKey() != null) {
            user.setSecretKey(individualUpdateDto.getSecretKey());
        }
        userService.updateUser(user);

        Address address = user.getAddress();

        if (individualUpdateDto.getAddress() != null) {
            address.setAddress(individualUpdateDto.getAddress());
        }
        if (individualUpdateDto.getZipCode() != null) {
            address.setZipCode(individualUpdateDto.getZipCode());
        }
        if (individualUpdateDto.getCity() != null) {
            address.setCity(individualUpdateDto.getCity());
        }
        if (individualUpdateDto.getState() != null) {
            address.setState(individualUpdateDto.getState());
        }

        addressService.updateAddress(address);

        Country country = address.getCountry();


    }

}
