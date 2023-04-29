package com.seungmoo.backend.api.service.vacation.factories;

import com.seungmoo.backend.api.service.common.protocols.response.Url;
import com.seungmoo.backend.api.service.vacation.protocols.responses.VacationListResponse;
import com.seungmoo.backend.api.service.vacation.protocols.responses.VacationResponse;
import com.seungmoo.backend.vacation.dtos.VacationDTO;
import com.seungmoo.backend.vacation.dtos.VacationTemplateDTO;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class VacationRetrieveFactory {

    public VacationListResponse toListResponse(VacationTemplateDTO vacationTemplateDTO) {
        return VacationListResponse.builder()
                .vacationTemplateId(vacationTemplateDTO.getVacationTemplateId())
                .year(vacationTemplateDTO.getYear())
                .maxVacationDays(vacationTemplateDTO.getMaxVacationCount())
                .reservedVacationDays(toReservedVacationDays(vacationTemplateDTO))
                .vacations(toVacationResponses(vacationTemplateDTO))
                .build();
    }

    private BigDecimal toReservedVacationDays(VacationTemplateDTO vacationTemplateDTO) {
        return vacationTemplateDTO.getVacations().stream()
                .map(VacationDTO::getDays)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private List<VacationResponse> toVacationResponses(VacationTemplateDTO vacationTemplateDTO) {
        return vacationTemplateDTO.getVacations().stream()
                .map(vacationDTO -> toVacationResponse(vacationTemplateDTO.getVacationTemplateId(), vacationDTO))
                .collect(Collectors.toList());
    }

    public VacationResponse toVacationResponse(Long vacationTemplateId, VacationDTO vacationDTO) {
        return VacationResponse.builder()
                .vacationId(vacationDTO.getVacationId())
                .vacationType(vacationDTO.getVacationType())
                .startDate(vacationDTO.getStartDate())
                .endDate(vacationDTO.getEndDate())
                .days(vacationDTO.getDays())
                .comment(vacationDTO.getComment())
                .urls(toUrls(vacationTemplateId, vacationDTO.getVacationId()))
                .build();
    }

    private Map<String, Url> toUrls(Long vacationTemplateId, Long vacationId) {
        return Map.of(
                "retrieve", Url.builder().method(HttpMethod.GET).href(String.format("/v1/vacationTemplates/%d/vacations/%d", vacationTemplateId, vacationId)).build(),
                "cancel", Url.builder().method(HttpMethod.DELETE).href(String.format("/v1/vacationTemplates/%d/vacations/%d", vacationTemplateId, vacationId)).build()
        );
    }

}
