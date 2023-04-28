package com.seungmoo.backend.vacation.dtos;

import com.seungmoo.backend.domain.constants.VacationType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VacationDTO {
    private Long vacationId;
    private VacationType vacationType;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal days;
    private String comment;
}
