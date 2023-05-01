package com.seungmoo.backend.api.presentation.vacation;

import com.seungmoo.backend.api.presentation.annotations.AuthRequired;
import com.seungmoo.backend.api.presentation.common.models.SessionUser;
import com.seungmoo.backend.api.service.common.protocols.response.Resource;
import com.seungmoo.backend.api.service.vacation.VacationActionSerivce;
import com.seungmoo.backend.api.service.vacation.VacationRetrieveService;
import com.seungmoo.backend.api.service.vacation.exceptions.VacationDoesNotExistException;
import com.seungmoo.backend.api.service.vacation.exceptions.VacationTemplateDoesNotExistException;
import com.seungmoo.backend.api.service.vacation.protocols.requests.VacationRequest;
import com.seungmoo.backend.api.service.vacation.protocols.responses.VacationListResponse;
import com.seungmoo.backend.api.service.vacation.protocols.responses.VacationResponse;
import com.seungmoo.backend.vacation.exceptions.VacationAddFailedException;
import com.seungmoo.backend.vacation.exceptions.VacationRemoveFailedException;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/vacationTemplates")
@RequiredArgsConstructor
public class VacationController {
    private final VacationRetrieveService vacationRetrieveService;
    private final VacationActionSerivce vacationActionSerivce;

    @AuthRequired
    @GetMapping
    public Resource<VacationListResponse> getVacationTemplate(@RequestParam("year") int year,
                                                              @Parameter(hidden = true) SessionUser sessionUser) {
        return Resource.<VacationListResponse>builder()
                .data(vacationRetrieveService.getVacationList(sessionUser.getUserId(), year))
                .build();
    }

    @AuthRequired
    @GetMapping("/{vacationTemplateId}/vacations/{vacationId}")
    public Resource<VacationResponse> getVacation(@PathVariable("vacationTemplateId") Long vacationTemplateId, @PathVariable("vacationId") Long vacationId,
                                                  @Parameter(hidden = true) SessionUser sessionUser) {
        return Resource.<VacationResponse>builder()
                .data(vacationRetrieveService.getVacation(sessionUser.getUserId(), vacationTemplateId, vacationId))
                .build();
    }

    @AuthRequired
    @PostMapping("/{vacationTemplateId}/vacations")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<HttpStatus> requestVacation(@PathVariable("vacationTemplateId") Long vacationTemplateId,
                                                @RequestBody VacationRequest vacationRequest,
                                                @Parameter(hidden = true) SessionUser sessionUser) {
        vacationActionSerivce.requestVacation(sessionUser.getUserId(), vacationTemplateId, vacationRequest);

        return Resource.<HttpStatus>builder()
                .data(HttpStatus.CREATED)
                .build();
    }

    @AuthRequired
    @DeleteMapping("/{vacationTemplateId}/vacations/{vacationId}")
    public Resource<HttpStatus> cancelVacation(@PathVariable("vacationTemplateId") Long vacationTemplateId, @PathVariable("vacationId") Long vacationId,
                                               @Parameter(hidden = true) SessionUser sessionUser) {
        vacationActionSerivce.cancelVacation(sessionUser.getUserId(), vacationTemplateId, vacationId);

        return Resource.<HttpStatus>builder()
                .data(HttpStatus.OK)
                .build();
    }

    @ExceptionHandler(VacationTemplateDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private Resource<String> handleVacationTemplateDoesNotExistException() {
        return Resource.<String>builder()
                .errorMessage("회원님의 vacation_template이 없습니다!")
                .errorType("vacation_template_does_not_exist_exception")
                .build();
    }

    @ExceptionHandler(VacationDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private Resource<String> handleVacationDoesNotExistException() {
        return Resource.<String>builder()
                .errorMessage("회원님이 요청하신 vacation이 없습니다!")
                .errorType("vacation_does_not_exist_exception")
                .build();
    }

    @ExceptionHandler({VacationAddFailedException.class, VacationRemoveFailedException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    private Resource<String> handleVacationActionFailedException(RuntimeException e) {
        return Resource.<String>builder()
                .errorMessage(e.getMessage())
                .errorType("vacation_action_failed_exception")
                .build();
    }

}
