package com.seungmoo.backend.api.presentation.vacation;

import com.seungmoo.backend.api.presentation.annotations.AuthRequired;
import com.seungmoo.backend.api.presentation.common.models.SessionUser;
import com.seungmoo.backend.api.presentation.templates.Resource;
import com.seungmoo.backend.api.service.vacation.VacationRetrieveService;
import com.seungmoo.backend.api.service.vacation.protocols.response.VacationListResponse;
import com.seungmoo.backend.api.service.vacation.protocols.response.VacationResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/vacationTemplates")
@RequiredArgsConstructor
public class VacationController {
    private final VacationRetrieveService vacationRetrieveService;

    @Parameter(in = ParameterIn.HEADER, name = HttpHeaders.AUTHORIZATION, required = true, description = "'Bearer {token}' 형식으로 넣어주세요.")
    @AuthRequired
    @GetMapping
    public Resource<VacationListResponse> getVacationTemplate(@RequestParam("year") int year,
                                                              SessionUser sessionUser) {
        return Resource.<VacationListResponse>builder()
                .data(vacationRetrieveService.getVacationList(sessionUser.getUserId(), year))
                .build();
    }

    @Parameter(in = ParameterIn.HEADER, name = HttpHeaders.AUTHORIZATION, required = true, description = "'Bearer {token}' 형식으로 넣어주세요.")
    @AuthRequired
    @GetMapping("/{vacationTemplateId}/vacations/{vacationId}")
    public Resource<VacationResponse> getVacation(@PathVariable("vacationTemplateId") Long vacationTemplateId, @PathVariable("vacationId") Long vacationId,
                                                  SessionUser sessionUser) {
        return Resource.<VacationResponse>builder()
                .data(vacationRetrieveService.getVacation(sessionUser.getUserId(), vacationTemplateId, vacationId))
                .build();
    }

}
