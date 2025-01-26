package com.umaraliev.personapi.service;


import com.umaraliev.personapi.dto.IndividualDTO;
import com.umaraliev.personapi.mappers.IndividualMapper;
import com.umaraliev.personapi.model.Individual;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final CountryService countryService;
    private final AddressService addressService;
    private final IndividualService individualService;
    private final UserHistoryService userHistoryService;
    private final IndividualMapper individualMapper;

    @Transactional
    public Individual registrationUser(IndividualDTO individualDto) {
        log.info("Registration user: {}", individualDto);
        try {
            return individualService.saveIndividual(individualMapper.toEntity(individualDto));
        } catch (Exception e) {
            log.error("Failed to register user: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to register user: " + e.getMessage(), e);
        }
    }


}
