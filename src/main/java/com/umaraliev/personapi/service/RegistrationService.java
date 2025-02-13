package com.umaraliev.personapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umaraliev.personapi.audit.AuditService;
import com.umaraliev.personapi.dto.IndividualDTO;
import com.umaraliev.personapi.dto.ResponseIndividualDTO;
import com.umaraliev.personapi.exception.NotCouldRegisterIndividualException;
import com.umaraliev.personapi.exception.NotFoundIndividualID;
import com.umaraliev.personapi.exception.NotCouldSaveIndividualException;
import com.umaraliev.personapi.mappers.IndividualMapper;
import com.umaraliev.personapi.model.Individual;
import com.umaraliev.personapi.model.UserHistory;
import com.umaraliev.personapi.utils.NullAwareBeanUtilsBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {


    private final IndividualService individualService;
    private final IndividualMapper individualMapper;
    private final AuditService auditService;
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;
    private final UserHistoryService userHistoryService;
    private final UserService userService;


    private  final Javers javers = JaversBuilder.javers().build();


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public ResponseIndividualDTO registrationUser(IndividualDTO individualDto) {
        log.info("Method: {registrationUser} was called with IndividualDTO: {}", individualDto);
        try {
            if (!userService.existsByEmail(individualDto.getUser().getEmail()) == false)
                throw new NotCouldRegisterIndividualException("User with email: " + individualDto.getUser().getEmail() + " already exists");
            log.info("Saving an individual: {}", individualDto);
            Individual individual = individualService.saveIndividual(individualMapper.toEntity(individualDto))
                    .orElseThrow(() -> new NotCouldRegisterIndividualException("Failed to register individual"));
            log.info("Saved individual his ID: {}", individual.getId());
            UserHistory userHistory = new UserHistory(
                    individual.getUser(),
                    "INDIVIDUAL",
                    "REGISTRATION",
                    "Initial registration",
                    objectMapper.writeValueAsString(individualDto)
            );
            userHistoryService.saveUserHistory(userHistory);
            ResponseIndividualDTO responseIndividualDTO = new ResponseIndividualDTO(
                    individual.getId(),
                    individual.getUser().getSecretKey(),
                    individual.getUser().getEmail()
            );
            return responseIndividualDTO;
        } catch (Exception e) {
            log.error("Failed to register individual: {}", e.getMessage(), e);
            throw new NotCouldRegisterIndividualException("Failed to register individual: " + e.getMessage(), e);
        }
    }

    @Transactional
    public ResponseIndividualDTO updateIndividual(UUID id, IndividualDTO individualDto) {
        log.info("Method: {updateIndividual} was called with IndividualDTO: {}", individualDto, id);
        Individual oldIndividual;
        try {
            if (!userService.existsByEmail(individualDto.getUser().getEmail()) == false)
                throw new NotCouldRegisterIndividualException("User with email: " + individualDto.getUser().getEmail() + " already exists");
            oldIndividual = individualService.getIndividualById(id)
                    .orElseThrow(() -> new NotFoundIndividualID("Individual not found with id: " + id));
            Diff diff = auditService.audit(id, individualDto);
            System.out.println(javers.getJsonConverter().toJson(diff));
            UserHistory userHistory= userHistoryService.getUserHistoryById(oldIndividual.getUser().getId())
                    .orElseThrow(() -> new NotFoundIndividualID("Individual history not found with id: " + id));
            userHistory.setChangedValues(objectMapper.writeValueAsString(individualDto));
            userHistoryService.updateUserHistory(userHistory);
            nullAwareBeanUtilsBean.copyNonNullProperties(oldIndividual, individualMapper.toEntity(individualDto));
            log.info("Proceeds the merger {}",oldIndividual);
            individualService.updateIndividual(oldIndividual);
            log.info("Updated individual with ID: {}", id);
        } catch (Exception e) {
            log.error("Failed to update individual: {}", e.getMessage(), e);
            throw new NotCouldSaveIndividualException("Failed to update individual: " + e.getMessage(), e);
        }
        ResponseIndividualDTO responseIndividualDTO = new ResponseIndividualDTO(
                oldIndividual.getId(),
                oldIndividual.getUser().getSecretKey(),
                oldIndividual.getUser().getEmail()
        );
        return responseIndividualDTO;
    }
}
