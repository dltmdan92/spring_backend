package com.seungmoo.backend.user.dtos;

import com.seungmoo.backend.domain.constants.RoleType;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDTO {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private List<RoleType> roles;
}
