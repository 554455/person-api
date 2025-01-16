package com.umaraliev.personapi.service;

import com.umaraliev.personapi.dto.IndividualUpdateDTO;
import com.umaraliev.personapi.model.Address;
import com.umaraliev.personapi.model.Country;
import com.umaraliev.personapi.model.User;
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
    public void updateUser(UUID id, IndividualUpdateDTO individualUpdateDto) {

        Optional<User> optionalUser = userService.getUserById(id);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found with id: " + id);
        }

        User user = optionalUser.get();

        updateField(user::setFirstName, individualUpdateDto.getFirstName());
        updateField(user::setLastName, individualUpdateDto.getLastName());
        updateField(user::setEmail, individualUpdateDto.getEmail());
        updateField(user::setSecretKey, individualUpdateDto.getSecretKey());

        Address address = user.getAddress();

        updateField(address::setAddress, individualUpdateDto.getAddress());
        updateField(address::setZipCode, individualUpdateDto.getZipCode());
        updateField(address::setCity, individualUpdateDto.getCity());
        updateField(address::setState, individualUpdateDto.getState());

        addressService.updateAddress(address);

        Country country = address.getCountry();

        if (individualUpdateDto.getCountry() != null) {
            country.setName(individualUpdateDto.getCountry());
            countryService.updateCountry(country);
        }

        countryService.updateCountry(country);
    }

    private <T> void updateField(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
