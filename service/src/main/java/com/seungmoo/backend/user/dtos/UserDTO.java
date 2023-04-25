package com.seungmoo.backend.user.dtos;

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
public class UserDTO {
    private Long userId;
    private String password;
    private String username;
    private String email;
    private List<RoleType> roles;
}
