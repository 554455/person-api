package com.umaraliev.personapi.service;

import com.umaraliev.personapi.model.Address;
import com.umaraliev.personapi.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public List<Address> getAddressRepository() {
        return addressRepository.findAll();
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address getAddressByID(UUID id) {
        return addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found with id: " + id));
    }

}
