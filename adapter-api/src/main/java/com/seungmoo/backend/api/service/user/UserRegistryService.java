package com.seungmoo.backend.api.service.user;

import com.seungmoo.backend.api.service.user.factories.UserDTOFactory;
import com.seungmoo.backend.api.service.user.protocols.requests.UserRegistryRequest;
import com.seungmoo.backend.api.service.vacation.protocols.events.CreateNewVacationTemplateEvent;
import com.seungmoo.backend.domain.aggregates.user.User;
import com.seungmoo.backend.user.UserService;
import com.seungmoo.backend.user.exceptions.UserEmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserRegistryService {

    private final UserService userService;

    private final UserDTOFactory userDTOFactory;

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     *
     * @param request
     * @throws UserEmailAlreadyExistsException
     */
    public void registUser(UserRegistryRequest request) {
        User user = userService.signUp(userDTOFactory.toUserDTO(request));
        applicationEventPublisher.publishEvent(new CreateNewVacationTemplateEvent(user.getUserId(), LocalDate.now().getYear(), 15));
    }

}
