package com.seungmoo.backend.api.service.vacation;

import com.seungmoo.backend.api.service.vacation.exceptions.VacationDoesNotExistsException;
import com.seungmoo.backend.api.service.vacation.exceptions.VacationTemplateDoesNotExistsException;
import com.seungmoo.backend.api.service.vacation.factories.VacationRetrieveFactory;
import com.seungmoo.backend.api.service.vacation.protocols.response.VacationListResponse;
import com.seungmoo.backend.api.service.vacation.protocols.response.VacationResponse;
import com.seungmoo.backend.vacation.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacationRetrieveService {

    private final VacationService vacationService;

    private final VacationRetrieveFactory vacationRetrieveFactory;

    public VacationListResponse getVacationList(Long userId, int year) {
        return vacationService.getVacationTemplateDTO(userId, year)
                .map(vacationRetrieveFactory::toListResponse)
                .orElseThrow(VacationTemplateDoesNotExistsException::new);
    }

    public VacationResponse getVacation(Long userId, Long vacationTemplateId, Long vacationId) {
        return vacationService.getVacationDTO(userId, vacationTemplateId, vacationId)
                .map(vacationDTO -> vacationRetrieveFactory.toVacationResponse(vacationTemplateId, vacationDTO))
                .orElseThrow(VacationDoesNotExistsException::new);
    }
}
