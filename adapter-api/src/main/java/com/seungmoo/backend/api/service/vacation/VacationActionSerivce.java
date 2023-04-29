package com.seungmoo.backend.api.service.vacation;

import com.seungmoo.backend.api.service.vacation.protocols.events.CreateNewVacationTemplateEvent;
import com.seungmoo.backend.api.service.vacation.protocols.requests.VacationRequest;
import com.seungmoo.backend.vacation.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacationActionSerivce {

    private final VacationService vacationService;

    public void requestVacation(Long userId, Long vacationTemplateId, VacationRequest vacationRequest) {
        vacationService.addVacation(userId, vacationTemplateId, vacationRequest.getVacationType(), vacationRequest.getStartDate(), vacationRequest.getEndDate(), vacationRequest.getComment());
    }

    public void cancelVacation(Long userId, Long vacationTemplateId, Long vacationId) {
        vacationService.deleteVacation(userId, vacationTemplateId, vacationId);
    }

    @EventListener
    public void handleCreateNewVacationTemplate(CreateNewVacationTemplateEvent event) {
        vacationService.createNewVacationTemplate(event.getUserId(), event.getYear(), event.getMaxVacationCount());
    }


}
