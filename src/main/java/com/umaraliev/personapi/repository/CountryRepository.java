package com.umaraliev.personapi.repository;

import com.umaraliev.personapi.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
