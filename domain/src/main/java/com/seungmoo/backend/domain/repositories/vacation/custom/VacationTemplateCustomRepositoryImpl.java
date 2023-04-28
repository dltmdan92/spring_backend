package com.seungmoo.backend.domain.repositories.vacation.custom;

import com.seungmoo.backend.domain.aggregates.vacation.QVacation;
import com.seungmoo.backend.domain.aggregates.vacation.QVacationTemplate;
import com.seungmoo.backend.domain.aggregates.vacation.VacationTemplate;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

public class VacationTemplateCustomRepositoryImpl extends QuerydslRepositorySupport implements VacationTemplateCustomRepository {
    private final QVacationTemplate vacationTemplate = QVacationTemplate.vacationTemplate;
    private final QVacation vacation = QVacation.vacation;

    public VacationTemplateCustomRepositoryImpl() {
        super(VacationTemplate.class);
    }

    @Override
    public Optional<VacationTemplate> getVacationTemplateByYear(Long userId, int year) {
        return Optional.ofNullable(
                from(vacationTemplate)
                        .leftJoin(vacation).on(vacationTemplate.vacationTemplateId.eq(vacation.vacationTemplate.vacationTemplateId)).fetchJoin()
                        .where(vacationTemplate.userId.eq(userId).and(vacationTemplate.year.eq(year)))
                        .fetchOne());
    }

    public Optional<VacationTemplate> getVacationTemplateByTemplateId(Long userId, Long vacationTemplateId) {
        return Optional.ofNullable(
                from(vacationTemplate)
                        .leftJoin(vacation).on(vacationTemplate.vacationTemplateId.eq(vacation.vacationTemplate.vacationTemplateId)).fetchJoin()
                        .where(vacationTemplate.vacationTemplateId.eq(vacationTemplateId).and(vacationTemplate.userId.eq(userId)))
                        .fetchOne());
    }
}
