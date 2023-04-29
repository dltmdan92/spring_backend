package com.seungmoo.backend.api.service.vacation.protocols.response;

import com.seungmoo.backend.api.service.common.protocols.response.Url;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacationListResponse {
    private Long vacationTemplateId;
    private int year;
    private int maxVacationDays;
    private BigDecimal reservedVacationDays;
    private List<VacationResponse> vacations = Collections.emptyList();
}
