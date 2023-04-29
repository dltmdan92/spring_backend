package com.seungmoo.backend.api.service.vacation.protocols.requests;

import com.seungmoo.backend.domain.constants.VacationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacationRequest {
    private VacationType vacationType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String comment;
}
