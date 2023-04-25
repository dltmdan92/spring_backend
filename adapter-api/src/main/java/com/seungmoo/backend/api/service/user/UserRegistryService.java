package com.seungmoo.backend.api.service.user;

import com.seungmoo.backend.api.service.user.factories.UserDTOFactory;
import com.seungmoo.backend.api.service.user.requests.UserRegistryRequest;
import com.seungmoo.backend.exceptions.UserEmailAlreadyExistsException;
import com.seungmoo.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistryService {

    private final UserService userService;

    private final UserDTOFactory userDTOFactory;

    /**
     *
     * @param request
     * @throws UserEmailAlreadyExistsException
     */
    public void registUser(UserRegistryRequest request) {
        userService.signUp(userDTOFactory.toUserDTO(request));
    }

}
