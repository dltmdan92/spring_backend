package com.seungmoo.backend.vacation.dtos;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VacationTemplateDTO {
    private Long vacationTemplateId;
    private int year;
    private Long userId;
    private int maxVacationCount;
    private List<VacationDTO> vacations;
}
