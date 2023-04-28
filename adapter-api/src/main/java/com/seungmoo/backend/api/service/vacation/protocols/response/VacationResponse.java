package com.seungmoo.backend.api.service.vacation.protocols.response;

import com.seungmoo.backend.api.service.common.protocols.response.Url;
import com.seungmoo.backend.domain.constants.VacationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacationResponse {
    private Long vacationId;
    private VacationType vacationType;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal days;
    private String comment;
    private Map<String, Url> urls;
}
