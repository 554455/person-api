package com.umaraliev.personapi.repository;

import com.umaraliev.personapi.model.Individual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IndividualRepository extends JpaRepository <Individual, UUID> {
}
