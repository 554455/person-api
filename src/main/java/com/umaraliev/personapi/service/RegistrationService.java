package com.umaraliev.personapi.service;

import com.umaraliev.personapi.audit.AuditService;
import com.umaraliev.personapi.dto.IndividualDTO;
import com.umaraliev.personapi.mappers.IndividualMapper;
import com.umaraliev.personapi.model.Individual;
import com.umaraliev.personapi.utils.NullAwareBeanUtilsBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional
    public Individual registrationUser(IndividualDTO individualDto) {
        log.info("Registration user: {}", individualDto);
        try {
            return individualService.saveIndividual(individualMapper.toEntity(individualDto));
        } catch (Exception e) {
            log.error("Failed to register user: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to register user: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void updateIndividual(UUID id, IndividualDTO individualDto) {
        log.info("Update user with ID: {}. New data: {}", id, individualDto);
        try {
            Individual oldIndividual = individualService.getIndividualById(id)
                    .orElseThrow(() -> new RuntimeException("Individual not found with id: " + id));

            Diff diff = auditService.audit(id, individualDto);

            nullAwareBeanUtilsBean.copyNonNullProperties(oldIndividual, individualMapper.toEntity(individualDto));
            log.info("Proceeds the merger {}",oldIndividual);

            individualService.updateIndividual(oldIndividual);
            log.info("Updated individual with ID: {}", id);
        } catch (Exception e) {
            log.error("Failed to update individual: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update individual: " + e.getMessage(), e);
        }
    }
}
