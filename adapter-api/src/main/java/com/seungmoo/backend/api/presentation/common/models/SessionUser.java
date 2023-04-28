package com.seungmoo.backend.api.presentation.common.models;

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
public class SessionUser {
    private Long userId;
    private String username;
    private String email;
    private List<RoleType> roles;
}
