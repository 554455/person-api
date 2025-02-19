package com.umaraliev.personapi.repository;

import com.umaraliev.personapi.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
