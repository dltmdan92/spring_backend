package com.seungmoo.backend.domain.repositories.vacation.custom;

import com.seungmoo.backend.domain.aggregates.vacation.VacationTemplate;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class VacationTemplateCustomRepositoryImpl extends QuerydslRepositorySupport implements VacationTemplateCustomRepository {

    public VacationTemplateCustomRepositoryImpl() {
        super(VacationTemplate.class);
    }
}
