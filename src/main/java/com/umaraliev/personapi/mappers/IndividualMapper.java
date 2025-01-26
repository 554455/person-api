package com.umaraliev.personapi.mappers;

import com.umaraliev.personapi.dto.IndividualDTO;
import com.umaraliev.personapi.model.Individual;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {UserMapper.class, AddressMapper.class, CountryMapper.class})
@Component
public interface IndividualMapper {
    Individual toEntity(IndividualDTO dto);

    IndividualDTO toDto(Individual entity);
}