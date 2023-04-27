package com.seungmoo.backend.user.mappers;

import com.seungmoo.backend.domain.aggregates.user.User;
import com.seungmoo.backend.user.dtos.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userDTO.getPassword()))")
    User toEntity(UserDTO userDTO, PasswordEncoder passwordEncoder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserDTO toDTO(User user);

}
