package com.umaraliev.personapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.umaraliev.personapi.dto.IndividualRegistrationDTO;
import com.umaraliev.personapi.dto.IndividualUpdateDTO;
import com.umaraliev.personapi.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registrationUser(@Valid @RequestBody IndividualRegistrationDTO individualRegistrationDto) throws JsonProcessingException {
        registrationService.registrationUser(individualRegistrationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/updateUserInfo/{id}")
    public ResponseEntity<HttpStatus> updateUser(@Valid @PathVariable UUID id, @RequestBody IndividualUpdateDTO individualUpdateDto){
        registrationService.updateUser(id, individualUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
