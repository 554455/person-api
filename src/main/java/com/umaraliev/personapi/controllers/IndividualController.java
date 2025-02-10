package com.umaraliev.personapi.controllers;

import com.umaraliev.personapi.dto.IndividualDTO;
import com.umaraliev.personapi.dto.ResponseIndividualDTO;
import com.umaraliev.personapi.exception.NotAccessDatabaseException;
import com.umaraliev.personapi.model.Individual;
import com.umaraliev.personapi.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/api/v1/auth")
@ComponentScan(basePackages = "com.umaraliev.personapi.service")
public class IndividualController {

    private final RegistrationService registrationService;

    @PostMapping("/registration")
    public ResponseEntity<ResponseIndividualDTO>  registrationUser(@Valid @RequestBody IndividualDTO individualDto){
        try {
            return new ResponseEntity<>(registrationService.registrationUser(individualDto), HttpStatus.OK);
        }catch (DataAccessException e){
            throw new NotAccessDatabaseException("Failed connect to the database: " + e.getMessage());
        }
    }

    @PutMapping("/updateUserInfo/{id}")
    public ResponseEntity<ResponseIndividualDTO> updateUser(@Valid @PathVariable UUID id, @RequestBody IndividualDTO individualUpdateDto){
        try {
            return new ResponseEntity<>(registrationService.updateIndividual(id, individualUpdateDto), HttpStatus.OK);
        }catch (DataAccessException e){
            throw new NotAccessDatabaseException("Failed connect to the database: " + e.getMessage());
        }
    }
}
