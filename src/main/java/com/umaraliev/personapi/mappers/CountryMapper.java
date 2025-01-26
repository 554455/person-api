package com.umaraliev.personapi.mappers;

import com.umaraliev.personapi.dto.IndividualDTO;
import com.umaraliev.personapi.model.Country;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CountryMapper {

    Country toCountryEntity(IndividualDTO.CountryDTO countryDTO);

    IndividualDTO.CountryDTO toCountryDto(Country country);
}
