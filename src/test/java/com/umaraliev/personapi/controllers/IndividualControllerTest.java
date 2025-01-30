package com.umaraliev.personapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umaraliev.personapi.service.RegistrationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(IndividualController.class)
class IndividualControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {

    }

    @Test
    void registrationUser() {
    }

    @Test
    void updateUser() {
    }
}