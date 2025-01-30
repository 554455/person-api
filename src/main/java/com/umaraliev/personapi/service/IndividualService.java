package com.umaraliev.personapi.service;

import com.umaraliev.personapi.model.Individual;
import com.umaraliev.personapi.repository.IndividualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IndividualService {

    private final IndividualRepository individualRepository;

    public List<Individual> getIndividualRepository() {
        return individualRepository.findAll();
    }

    public Optional<Individual> saveIndividual(Individual individual) {
        return Optional.of(individualRepository.save(individual));
    }

    public Individual updateIndividual(Individual individual) {
        return individualRepository.save(individual);
    }

    public Optional<Individual> getIndividualById(UUID id) {
        return Optional.ofNullable(individualRepository.findById(id).orElse(null));
    }

    public Boolean existsById(UUID id) {
        return individualRepository.existsById(id);
    }
}
