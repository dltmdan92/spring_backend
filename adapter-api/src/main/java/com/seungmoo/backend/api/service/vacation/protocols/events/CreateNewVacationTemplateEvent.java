package com.seungmoo.backend.api.service.vacation.protocols.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewVacationTemplateEvent {
    private Long userId;
    private int year;
    private int maxVacationCount;
}
