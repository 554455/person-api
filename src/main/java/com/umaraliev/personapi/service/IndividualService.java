package com.umaraliev.personapi.service;

import com.umaraliev.personapi.model.Individual;
import com.umaraliev.personapi.repository.IndividualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndividualService {

    private final IndividualRepository individualRepository;

    public List<Individual> getIndividualRepository() {
        return individualRepository.findAll();
    }

    public Individual saveIndividual(Individual individual) {
        return individualRepository.save(individual);
    }

    public Individual updateIndividual(Individual individual) {
        return individualRepository.save(individual);
    }
}
