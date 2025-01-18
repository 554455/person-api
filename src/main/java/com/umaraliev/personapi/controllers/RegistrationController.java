package com.umaraliev.personapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.umaraliev.personapi.dto.IndividualDTO;
import com.umaraliev.personapi.service.RegistrationService;
import com.umaraliev.personapi.service.UpdateUserInfoService;
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
    private final UpdateUserInfoService updateUserInfoService;

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registrationUser(@Valid @RequestBody IndividualDTO individualDto) throws JsonProcessingException {
        registrationService.registrationUser(individualDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/updateUserInfo/{id}")
    public ResponseEntity<HttpStatus> updateUser(@Valid @PathVariable UUID id, @RequestBody IndividualDTO individualUpdateDto){
        updateUserInfoService.updateUser(id, individualUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
