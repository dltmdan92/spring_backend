package com.seungmoo.backend.api.service.vacation;

import com.seungmoo.backend.api.service.vacation.exceptions.VacationDoesNotExistException;
import com.seungmoo.backend.api.service.vacation.exceptions.VacationTemplateDoesNotExistException;
import com.seungmoo.backend.api.service.vacation.factories.VacationRetrieveFactory;
import com.seungmoo.backend.api.service.vacation.protocols.responses.VacationListResponse;
import com.seungmoo.backend.api.service.vacation.protocols.responses.VacationResponse;
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
                .orElseThrow(VacationTemplateDoesNotExistException::new);
    }

    public VacationResponse getVacation(Long userId, Long vacationTemplateId, Long vacationId) {
        return vacationService.getVacationDTO(userId, vacationTemplateId, vacationId)
                .map(vacationDTO -> vacationRetrieveFactory.toVacationResponse(vacationTemplateId, vacationDTO))
                .orElseThrow(VacationDoesNotExistException::new);
    }
}
