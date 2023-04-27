package com.seungmoo.backend.api.service.user.requests;

import com.seungmoo.backend.domain.constants.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistryRequest {
    private String username;
    private String password;
    private String email;
    private List<RoleType> roles;
}
