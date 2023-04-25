package com.seungmoo.backend.domain.repositories.vacation;

import com.seungmoo.backend.domain.aggregates.vacation.VacationTemplate;
import com.seungmoo.backend.domain.repositories.vacation.custom.VacationTemplateCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacationTemplateRepository extends JpaRepository<VacationTemplate, Long>, VacationTemplateCustomRepository {
}
