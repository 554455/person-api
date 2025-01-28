package com.umaraliev.personapi.controllers;

import com.umaraliev.personapi.dto.IndividualDTO;
import com.umaraliev.personapi.model.Individual;
import com.umaraliev.personapi.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController("/api/v1/auth")
@ComponentScan(basePackages = "com.umaraliev.personapi.service")
public class IndividualController {

    private final RegistrationService registrationService;

    @PostMapping("/registration")
    public Individual registrationUser(@Valid @RequestBody IndividualDTO individualDto){
        return registrationService.registrationUser(individualDto);
    }

    @PutMapping("/updateUserInfo/{id}")
    public ResponseEntity<HttpStatus> updateUser(@Valid @PathVariable UUID id, @RequestBody IndividualDTO individualUpdateDto){
        registrationService.updateIndividual(id, individualUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
