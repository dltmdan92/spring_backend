package com.seungmoo.backend.batch.jobs.newyearvacation.writer;

import com.seungmoo.backend.vacation.VacationService;
import com.seungmoo.backend.vacation.dtos.VacationTemplateDTO;
import org.springframework.batch.item.ItemWriter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NewYearVacationTemplateWriter implements ItemWriter<Long> {
    private final VacationService vacationService;

    public NewYearVacationTemplateWriter(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @Override
    public void write(List<? extends Long> items) {
        List<Long> userIds = items.stream()
                .map(Long.class::cast)
                .collect(Collectors.toList());

        Map<Long, VacationTemplateDTO> vacationTemplateUserIdMap = vacationService.getVacationTemplates(userIds, LocalDate.now().getYear()).stream()
                .collect(Collectors.toMap(VacationTemplateDTO::getUserId, Function.identity(), (e, r) -> e));

        List<Long> userIdsWithoutVacationTemplate = userIds.stream()
                .filter(userId -> !vacationTemplateUserIdMap.containsKey(userId))
                .collect(Collectors.toList());

        vacationService.createNewVacationTemplate(userIdsWithoutVacationTemplate, LocalDate.now().getYear(), 15);
    }
}
