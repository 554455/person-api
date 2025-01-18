package com.umaraliev.personapi.repository;

import com.umaraliev.personapi.model.Individual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface IndividualRepository extends JpaRepository <Individual, UUID> {

    @Query("SELECT i FROM Individual i WHERE i.user.id = :userId")
    Individual findByUser(@Param("userId") UUID userId);
}
