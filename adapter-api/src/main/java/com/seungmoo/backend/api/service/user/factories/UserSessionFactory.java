package com.seungmoo.backend.api.service.user.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.seungmoo.backend.api.presentation.common.models.SessionUser;
import com.seungmoo.backend.configuration.utils.ObjectMapperUtils;
import com.seungmoo.backend.domain.constants.RoleType;
import com.seungmoo.backend.user.dtos.UserDTO;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserSessionFactory {
    private final String USERNAME = "username";
    private final String USERID = "userId";
    private final String EMAIL = "email";
    private final String ROLES = "roles";

    private final TypeReference<List<RoleType>> rolesTypeRef = new TypeReference<>() {};

    public Map<String, Object> toClaims(UserDTO userDTO) throws JsonProcessingException {
        return Map.of(
                USERNAME, userDTO.getUsername(),
                USERID, userDTO.getUserId(),
                EMAIL, userDTO.getEmail(),
                ROLES, ObjectMapperUtils.toString(userDTO.getRoles())
        );
    }

    public SessionUser toSessionUser(Claims claims) throws JsonProcessingException {
        return SessionUser.builder()
                .username(claims.get(USERNAME, String.class))
                .userId(claims.get(USERID, Long.class))
                .email(claims.get(EMAIL, String.class))
                .roles(ObjectMapperUtils.readString(claims.get(ROLES, String.class), rolesTypeRef))
                .build();
    }

}
