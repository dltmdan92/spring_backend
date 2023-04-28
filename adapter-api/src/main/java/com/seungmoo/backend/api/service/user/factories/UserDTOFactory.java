package com.seungmoo.backend.api.service.user.factories;

import com.seungmoo.backend.api.service.user.protocols.requests.UserRegistryRequest;
import com.seungmoo.backend.user.dtos.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDTOFactory {

    public UserDTO toUserDTO(UserRegistryRequest request) {
        return UserDTO.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .roles(request.getRoles())
                .build();
    }

}
