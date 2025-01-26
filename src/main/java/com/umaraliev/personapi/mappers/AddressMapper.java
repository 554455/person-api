package com.umaraliev.personapi.mappers;

import com.umaraliev.personapi.dto.IndividualDTO;
import com.umaraliev.personapi.model.Address;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = CountryMapper.class)
@Component
public interface AddressMapper {

    Address toAddressEntity(IndividualDTO.AddressDTO addressDTO);

    IndividualDTO.AddressDTO toAddressDto(Address address);
}
