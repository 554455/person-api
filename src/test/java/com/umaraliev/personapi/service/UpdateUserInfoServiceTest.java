package com.umaraliev.personapi.service;

import com.umaraliev.personapi.dto.IndividualDTO;
import com.umaraliev.personapi.model.Address;
import com.umaraliev.personapi.model.Country;
import com.umaraliev.personapi.model.User;
import com.umaraliev.personapi.model.UserHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserInfoServiceTest {

    @Mock
    UserService userService;

    @Mock
    CountryService countryService;

    @Mock
    AddressService addressService;

    @Mock
    IndividualService individualService;

    @Mock
    UserHistoryService userHistoryService;

    @InjectMocks
    UpdateUserInfoService updateUserInfoService;

    private User user;
    private Address address;
    private Country country;
    private UserHistory userHistory;
    private IndividualDTO individualDto;


    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setSecretKey("secretKey123");

        address = new Address();
        address.setId(UUID.randomUUID());
        address.setAddress("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setZipCode("12345");
        address.setCountry(country);

        country = new Country();
        country.setId(1L);
        country.setName("USA");

        user.setAddress(address);

        userHistory = new UserHistory();
        userHistory.setId(UUID.randomUUID());
        userHistory.setUser(user);
        userHistory.setUserType("INDIVIDUAL");
        userHistory.setReason("REGISTRATION");
        userHistory.setComment("Initial registration");

        individualDto = new IndividualDTO();
        individualDto.setFirstName("John Updated");
        individualDto.setLastName("Doe Updated");
        individualDto.setEmail("john.updated@example.com");
        individualDto.setSecretKey("secretKeyUpdated");
        individualDto.setAddress("456 Elm St");
        individualDto.setCity("New City");
        individualDto.setState("NY");
        individualDto.setZipCode("67890");
        individualDto.setCountry("Canada");
        individualDto.setPassportNumber("NEWPASS123");
        individualDto.setPhoneNumber("+1234567890");

        when(userService.getUserById(user.getId())).thenReturn(Optional.of(user));
        when(addressService.updateAddress(any(Address.class))).thenReturn(address);
        when(countryService.saveCountry(any(Country.class))).thenReturn(country);
        when(userHistoryService.getUserHistoryById(user.getId())).thenReturn(userHistory);
        when(userHistoryService.saveUserHistory(any(UserHistory.class))).thenReturn(userHistory);
    }

    @Test
    public void testUpdateUser_Success() {
        // Arrange
        UUID userId = user.getId();

        // Act
        updateUserInfoService.updateUser(userId, individualDto);

        // Assert
        verify(userService, times(1)).getUserById(userId);
        verify(addressService, times(1)).updateAddress(address);
        verify(countryService, times(1)).saveCountry(country);
        verify(userHistoryService, times(1)).getUserHistoryById(userId);
        verify(userHistoryService, times(1)).saveUserHistory(userHistory);

        assertEquals("John Updated", user.getFirstName());
        assertEquals("Doe Updated", user.getLastName());
        assertEquals("john.updated@example.com", user.getEmail());
        assertEquals("secretKeyUpdated", user.getSecretKey());

        assertEquals("456 Elm St", address.getAddress());
        assertEquals("New City", address.getCity());
        assertEquals("NY", address.getState());
        assertEquals("67890", address.getZipCode());

        assertEquals("Canada", country.getName());

        assertEquals("Update", userHistory.getReason());
        assertEquals("Update successful", userHistory.getComment());
        assertEquals(individualDto, userHistory.getChangedValues());
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        // Arrange
        UUID userId = UUID.randomUUID();

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            updateUserInfoService.updateUser(userId, individualDto);
        });

        assertEquals("User not found with id: " + userId, exception.getMessage());

        verify(userService, times(1)).getUserById(userId);
        verify(addressService, never()).updateAddress(any(Address.class));
        verify(countryService, never()).saveCountry(any(Country.class));
        verify(userHistoryService, never()).getUserHistoryById(userId);
        verify(userHistoryService, never()).saveUserHistory(any(UserHistory.class));
    }
}