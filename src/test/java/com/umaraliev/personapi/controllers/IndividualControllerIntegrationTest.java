package com.umaraliev.personapi.controllers;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umaraliev.personapi.dto.IndividualDTO;
import com.umaraliev.personapi.dto.ResponseIndividualDTO;
import com.umaraliev.personapi.model.Individual;
import com.umaraliev.personapi.service.IndividualService;
import com.umaraliev.personapi.service.PersonAPIService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IndividualControllerIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(IndividualControllerIntegrationTest.class);
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IndividualService individualService;

    @Autowired
    private TestRestTemplate restTemplate;

    private static UUID id;

    public static IndividualDTO createTestIndividualDTO() {
        IndividualDTO individualDTO = new IndividualDTO();
        individualDTO.setPassportNumber("P1234567");
        individualDTO.setPhoneNumber("555-1234");

        IndividualDTO.UserDTO user = new IndividualDTO.UserDTO();
        user.setEmail("test@example.com");
        user.setSecretKey("secret123");
        user.setFirstName("John");
        user.setLastName("Doe");

        IndividualDTO.AddressDTO address = new IndividualDTO.AddressDTO();
        address.setAddress("123 Main St");
        address.setCity("Test City");
        address.setState("Test State");
        address.setZipCode("12345");

        IndividualDTO.CountryDTO country = new IndividualDTO.CountryDTO();
        country.setName("Test Country");
        address.setCountry(country);

        user.setAddress(address);
        individualDTO.setUser(user);

        return individualDTO;
    }


    @Test
    @Order(1)
    public void testRegistrationUser() throws Exception {
        IndividualDTO individualDTO = createTestIndividualDTO();
        String json = objectMapper.writeValueAsString(individualDTO);

        ResponseEntity<ResponseIndividualDTO> responseEntity = restTemplate.postForEntity(
                "/api/v1/auth/registration",
                new HttpEntity<>(createTestIndividualDTO()), ResponseIndividualDTO.class);
        assertNotNull(responseEntity.getBody());
        log.info("Response status *testRegistrationUser()* code: {}", responseEntity);


        id=responseEntity.getBody().getId();

        Optional<Individual> individual = individualService.getIndividualById(id);
        Boolean isFilled = individualService.existsById(id);

        assertEquals(true, isFilled);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(createTestIndividualDTO().getUser().getEmail(), responseEntity.getBody().getEmail());
        assertEquals(createTestIndividualDTO().getUser().getSecretKey(), responseEntity.getBody().getSecretKey());
        assertEquals(individual.orElseThrow().getPassportNumber(), createTestIndividualDTO().getPassportNumber());
        assertEquals(individual.orElseThrow().getPhoneNumber(), createTestIndividualDTO().getPhoneNumber());
    }

    @Test
    @Order(2)
    public void testUpdateUser() throws Exception {
        IndividualDTO individualDTO = createTestIndividualDTO();
        individualDTO.setPhoneNumber("333-5678");
        individualDTO.getUser().setEmail("test_update@example.com");
        individualDTO.getUser().getAddress().setAddress("Updated Address");
        individualDTO.getUser().getAddress().getCountry().setName("Updated Country");

        ResponseEntity<ResponseIndividualDTO> responseEntity = restTemplate.exchange(
                "/api/v1/auth/update/" + id.toString(),
                HttpMethod.PUT,
                new HttpEntity<>(individualDTO),
                ResponseIndividualDTO.class
        );
        log.info("Response status *testUpdateUser()* code: {}", responseEntity);

        Optional<Individual> individual = individualService.getIndividualById(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(individual.orElseThrow().getPhoneNumber(), individualDTO.getPhoneNumber());
        assertEquals(individual.orElseThrow().getUser().getEmail(), individualDTO.getUser().getEmail());
        assertEquals(individual.orElseThrow().getUser().getAddress().getAddress(), individualDTO.getUser().getAddress().getAddress());
        assertEquals(individual.orElseThrow().getUser().getAddress().getCountry().getName(), individualDTO.getUser().getAddress().getCountry().getName());
    }
}
