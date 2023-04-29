package com.seungmoo.backend.domain.repositories.vacation.custom;

import com.seungmoo.backend.domain.aggregates.vacation.VacationTemplate;

import java.util.List;
import java.util.Optional;

public interface VacationTemplateCustomRepository {
    Optional<VacationTemplate> getVacationTemplateByYear(Long userId, int year);
    Optional<VacationTemplate> getVacationTemplateByTemplateId(Long userId, Long vacationTemplateId);
    List<VacationTemplate> getVacationTemplates(List<Long> userIds, int year);
}
