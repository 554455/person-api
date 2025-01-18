package com.umaraliev.personapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.umaraliev.personapi.dto.IndividualDTO;
import com.umaraliev.personapi.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private CountryService countryService;

    @Mock
    private AddressService addressService;

    @Mock
    private IndividualService individualService;

    @Mock
    private UserHistoryService userHistoryService;

    @InjectMocks
    private RegistrationService registrationService;

    private IndividualDTO individualDto;
    private User user;
    private Country country;
    private Address address;
    private Individual individual;
    private UserHistory userHistory;

    @BeforeEach
    public void setUp() {
        individualDto = new IndividualDTO();
        individualDto.setFirstName("John");
        individualDto.setLastName("Doe");
        individualDto.setEmail("john.doe@example.com");
        individualDto.setSecretKey("secretKey123");
        individualDto.setAddress("123 Main St");
        individualDto.setCity("Anytown");
        individualDto.setState("CA");
        individualDto.setZipCode("12345");
        individualDto.setCountry("USA");
        individualDto.setPassportNumber("PASS123456");
        individualDto.setPhoneNumber("+1234567890");

        user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName(individualDto.getFirstName());
        user.setLastName(individualDto.getLastName());
        user.setEmail(individualDto.getEmail());
        user.setSecretKey(individualDto.getSecretKey());
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());

        country = new Country();
        country.setId(1L);
        country.setName(individualDto.getCountry());
        country.setCreated(LocalDateTime.now());
        country.setUpdated(LocalDateTime.now());

        address = new Address();
        address.setId(UUID.randomUUID());
        address.setAddress(individualDto.getAddress());
        address.setCity(individualDto.getCity());
        address.setState(individualDto.getState());
        address.setZipCode(individualDto.getZipCode());
        address.setCreated(LocalDateTime.now());
        address.setUpdated(LocalDateTime.now());
        address.setArchived(LocalDateTime.now());
        address.setCountry(country);

        individual = new Individual();
        individual.setId(UUID.randomUUID());
        individual.setPassportNumber(individualDto.getPassportNumber());
        individual.setPhoneNumber(individualDto.getPhoneNumber());
        individual.setEmail(individualDto.getEmail());
        individual.setVerifiedAt(LocalDateTime.now());
        individual.setArchivedAt(LocalDateTime.now());
        individual.setStatus("ACTIVE");
        individual.setUser(user);

        userHistory = new UserHistory();
        userHistory.setId(UUID.randomUUID());
        userHistory.setUser(user);
        userHistory.setUserType("INDIVIDUAL");
        userHistory.setReason("REGISTRATION");
        userHistory.setCreated(LocalDateTime.now());
        userHistory.setComment("Registration successful");
        userHistory.setChangedValues(individualDto);

        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(countryService.saveCountry(any(Country.class))).thenReturn(country);
        when(addressService.saveAddress(any(Address.class))).thenReturn(address);
        when(individualService.saveIndividual(any(Individual.class))).thenReturn(individual);
        when(userHistoryService.saveUserHistory(any(UserHistory.class))).thenReturn(userHistory);
    }

    @Test
    public void testRegistrationUser_Success() throws JsonProcessingException {
        // Arrange
        UUID userId = user.getId();

        // Act
        User registeredUser = registrationService.registrationUser(individualDto);

        // Assert
        assertNotNull(registeredUser);
        assertEquals(userId, registeredUser.getId());
        assertEquals("John", registeredUser.getFirstName());
        assertEquals("Doe", registeredUser.getLastName());
        assertEquals("john.doe@example.com", registeredUser.getEmail());
        assertEquals("secretKey123", registeredUser.getSecretKey());

        verify(userService, times(2)).saveUser(any(User.class));
        verify(countryService, times(1)).saveCountry(any(Country.class));
        verify(addressService, times(1)).saveAddress(any(Address.class));
        verify(individualService, times(1)).saveIndividual(any(Individual.class));
        verify(userHistoryService, times(1)).saveUserHistory(any(UserHistory.class));
    }

    @Test
    public void testRegistrationUser_Failure() throws JsonProcessingException {
        // Arrange
        when(userService.saveUser(any(User.class))).thenThrow(new RuntimeException("Failed to save user"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            registrationService.registrationUser(individualDto);
        });

        assertEquals("Failed to register user: Failed to save user", exception.getMessage());

        verify(userService, times(1)).saveUser(any(User.class));
        verify(countryService, never()).saveCountry(any(Country.class));
        verify(addressService, never()).saveAddress(any(Address.class));
        verify(individualService, never()).saveIndividual(any(Individual.class));
        verify(userHistoryService, never()).saveUserHistory(any(UserHistory.class));
    }
}