package com.umaraliev.personapi.mappers;

import com.umaraliev.personapi.dto.IndividualDTO;
import com.umaraliev.personapi.model.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, CountryMapper.class})
@Component
public interface UserMapper {

    User toUserEntity(IndividualDTO.UserDTO userDTO);

    IndividualDTO.UserDTO toUserDto(User user);
}