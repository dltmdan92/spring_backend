package com.seungmoo.backend.user.mappers;

import com.seungmoo.backend.domain.aggregates.user.User;
import com.seungmoo.backend.user.dtos.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userDTO.getPassword()))")
    User toEntity(UserDTO userDTO, PasswordEncoder passwordEncoder);

    UserDTO toDTO(User user);

}
