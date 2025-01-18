package com.umaraliev.personapi.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.umaraliev.personapi.dto.IndividualDTO;
import com.umaraliev.personapi.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final CountryService countryService;
    private final AddressService addressService;
    private final IndividualService individualService;
    private final UserHistoryService userHistoryService;

    @Transactional
    public User registrationUser(IndividualDTO individualDto) throws JsonProcessingException {

       try {
           User user = new User();
           user.setFirstName(individualDto.getFirstName());
           user.setLastName(individualDto.getLastName());
           user.setEmail(individualDto.getEmail());
           user.setSecretKey(individualDto.getSecretKey());
           user.setCreated(LocalDateTime.now());
           user.setUpdated(LocalDateTime.now());
           userService.saveUser(user);

           System.out.println(user.getId());


           Country country = new Country();
           country.setName(individualDto.getCountry());
           country.setCreated(LocalDateTime.now());
           country.setUpdated(LocalDateTime.now());
           countryService.saveCountry(country);

           Address address = new Address();
           address.setAddress(individualDto.getAddress());
           address.setCity(individualDto.getCity());
           address.setState(individualDto.getState());
           address.setZipCode(individualDto.getZipCode());
           address.setCreated(LocalDateTime.now());
           address.setUpdated(LocalDateTime.now());
           address.setArchived(LocalDateTime.now());
           address.setCountry(country);
           addressService.saveAddress(address);
           user.setAddress(address);
           userService.saveUser(user);

           Individual individual = new Individual();
           individual.setPassportNumber(individualDto.getPassportNumber());
           individual.setPhoneNumber(individualDto.getPhoneNumber());
           individual.setEmail(individualDto.getEmail());
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
           userHistory.setComment("Registration successful");
           userHistory.setChangedValues(individualDto);
           userHistoryService.saveUserHistory(userHistory);
           userService.saveUser(user);
           return user;
       } catch (Exception e) {
           throw new RuntimeException("Failed to register user: " + e.getMessage());
       }
    }


}
