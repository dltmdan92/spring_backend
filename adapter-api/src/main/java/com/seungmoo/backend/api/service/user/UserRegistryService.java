package com.seungmoo.backend.api.service.user;

import com.seungmoo.backend.api.service.user.factories.UserDTOFactory;
import com.seungmoo.backend.api.service.user.protocols.requests.UserRegistryRequest;
import com.seungmoo.backend.domain.aggregates.user.User;
import com.seungmoo.backend.user.exceptions.UserEmailAlreadyExistsException;
import com.seungmoo.backend.user.UserService;
import com.seungmoo.backend.vacation.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserRegistryService {

    private final UserService userService;
    private final VacationService vacationService;

    private final UserDTOFactory userDTOFactory;

    /**
     *
     * @param request
     * @throws UserEmailAlreadyExistsException
     */
    @Transactional
    public void registUser(UserRegistryRequest request) {
        User user = userService.signUp(userDTOFactory.toUserDTO(request));
        vacationService.createNewVacationTemplate(user.getUserId(), LocalDate.now().getYear(), 15);
    }

}
