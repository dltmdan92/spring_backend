package com.seungmoo.backend.api.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seungmoo.backend.api.service.user.providers.SessionProvider;
import com.seungmoo.backend.api.service.user.protocols.requests.UserLoginRequest;
import com.seungmoo.backend.user.exceptions.UserNotExistsException;
import com.seungmoo.backend.user.UserService;
import com.seungmoo.backend.user.dtos.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLoginService {
    private final UserService userService;
    private final SessionProvider sessionProvider;

    /**
     *
     * @param request
     * @return
     * @throws UserNotExistsException
     */
    public String getToken(UserLoginRequest request) throws JsonProcessingException {
        Optional<UserDTO> user = userService.getUser(request.getEmail(), request.getPassword());

        if (user.isEmpty()) {
            throw new UserNotExistsException(request.getEmail());
        }

        return sessionProvider.createSessionToken(user.get());
    }
}
