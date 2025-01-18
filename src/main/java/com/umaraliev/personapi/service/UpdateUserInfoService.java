package com.umaraliev.personapi.service;

import com.umaraliev.personapi.dto.IndividualDTO;
import com.umaraliev.personapi.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class UpdateUserInfoService {

    private final UserService userService;
    private final CountryService countryService;
    private final AddressService addressService;
    private final IndividualService individualService;
    private final UserHistoryService userHistoryService;

    @Transactional
    public void updateUser(UUID id, IndividualDTO individualDto) {

        Optional<User> optionalUser = userService.getUserById(id);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found with id: " + id);
        }

        User user = optionalUser.get();
        updateField(user::setFirstName, individualDto.getFirstName());
        updateField(user::setLastName, individualDto.getLastName());
        updateField(user::setEmail, individualDto.getEmail());
        updateField(user::setSecretKey, individualDto.getSecretKey());
        userService.updateUser(user);

        Individual individual = individualService.getIndividualById(user.getId());

        updateField(individual::setPassportNumber, individualDto.getPassportNumber());
        updateField(individual::setPhoneNumber, individualDto.getPhoneNumber());
        updateField(individual::setEmail, individualDto.getEmail());
        individualService.updateIndividual(individual);

        Address address = user.getAddress();

        updateField(address::setAddress, individualDto.getAddress());
        updateField(address::setZipCode, individualDto.getZipCode());
        updateField(address::setCity, individualDto.getCity());
        updateField(address::setState, individualDto.getState());
        addressService.updateAddress(address);

        Country country = address.getCountry();

        if (individualDto.getCountry() != null) {
            country.setName(individualDto.getCountry());
            countryService.updateCountry(country);
        }

        countryService.updateCountry(country);

        UserHistory userHistory = userHistoryService.getUserHistoryById(user.getId());
        userHistory.setReason("Update");
        userHistory.setComment("Update successful");
        userHistory.setChangedValues(individualDto);
        userHistoryService.saveUserHistory(userHistory);
    }

    private <T> void updateField(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
