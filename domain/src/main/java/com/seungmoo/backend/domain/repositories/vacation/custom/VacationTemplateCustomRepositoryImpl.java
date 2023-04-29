package com.seungmoo.backend.domain.repositories.vacation.custom;

import com.seungmoo.backend.domain.aggregates.vacation.QVacation;
import com.seungmoo.backend.domain.aggregates.vacation.QVacationTemplate;
import com.seungmoo.backend.domain.aggregates.vacation.VacationTemplate;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
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
                        .leftJoin(vacationTemplate.vacations, vacation).fetchJoin()
                        .where(vacationTemplate.userId.eq(userId).and(vacationTemplate.year.eq(year)))
                        .fetchOne());
    }

    @Override
    public Optional<VacationTemplate> getVacationTemplateByTemplateId(Long userId, Long vacationTemplateId) {
        return Optional.ofNullable(
                from(vacationTemplate)
                        .leftJoin(vacationTemplate.vacations, vacation).fetchJoin()
                        .where(vacationTemplate.vacationTemplateId.eq(vacationTemplateId).and(vacationTemplate.userId.eq(userId)))
                        .fetchOne());
    }

    @Override
    public List<VacationTemplate> getVacationTemplates(List<Long> userIds, int year) {
        return from(vacationTemplate)
                .leftJoin(vacationTemplate.vacations, vacation).fetchJoin()
                .where(vacationTemplate.userId.in(userIds).and(vacationTemplate.year.eq(year)))
                .fetch();
    }
}
