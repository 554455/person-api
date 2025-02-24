package com.umaraliev.personapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umaraliev.personapi.audit.AuditService;
import com.umaraliev.personapi.dto.IndividualDTO;
import com.umaraliev.personapi.mappers.IndividualMapper;
import com.umaraliev.personapi.model.Individual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonAPIServiceTest {

    @Mock
    private IndividualService individualService;

    @Mock
    private IndividualMapper individualMapper;

    @Mock
    private AuditService auditService;

    @Mock
    private UserHistoryService userHistoryService;

    @Mock
    private UserService userService;

    @Mock
    private IndividualMapper individualMapperMock;

    @InjectMocks
    private PersonAPIService personAPIService;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public static IndividualDTO createSampleIndividualDTO() {
        IndividualDTO.CountryDTO country = new IndividualDTO.CountryDTO();
        country.setName("USA");

        IndividualDTO.AddressDTO address = new IndividualDTO.AddressDTO();
        address.setAddress("123 Main St");
        address.setCity("New York");
        address.setState("NY");
        address.setZipCode("10001");
        address.setCountry(country);

        IndividualDTO.UserDTO user = new IndividualDTO.UserDTO();
        user.setEmail("test@example.com");
        user.setSecretKey("secret123");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setAddress(address);

        IndividualDTO individualDTO = new IndividualDTO();
        individualDTO.setPassportNumber("A1234567");
        individualDTO.setPhoneNumber("+1234567890");
        individualDTO.setUser(user);

        return individualDTO;
    }


    @Test
    void registrationUser() {

        when(userService.existsByEmail("some@mail.ru")).thenReturn(false);
        when(userService.existsByEmail("user1@example.com")).thenReturn(true);

        Individual individual = individualMapper.toEntity(createSampleIndividualDTO());

        when(individualService.saveIndividual(individual)).thenReturn(Optional.ofNullable(individual));
    }

    @Test
    void updateIndividual() {

        when(userService.existsByEmail("some@mail.ru")).thenReturn(false);
        when(userService.existsByEmail("user1@example.com")).thenReturn(true);

        Individual individual = individualMapper.toEntity(createSampleIndividualDTO());

        when(individualService.saveIndividual(individual)).thenReturn(Optional.ofNullable(individual));
    }
}