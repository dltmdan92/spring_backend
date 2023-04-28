package com.seungmoo.backend.vacation;

import com.seungmoo.backend.domain.aggregates.vacation.VacationTemplate;
import com.seungmoo.backend.domain.repositories.vacation.VacationTemplateRepository;
import com.seungmoo.backend.vacation.dtos.VacationDTO;
import com.seungmoo.backend.vacation.dtos.VacationTemplateDTO;
import com.seungmoo.backend.vacation.mappers.VacationTemplateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacationService {

    private final VacationTemplateRepository vacationTemplateRepository;

    private final VacationTemplateMapper vacationTemplateMapper;

    @Transactional
    public void createNewVacationTemplate(Long userId, int year, int maxVacationCount) {
        VacationTemplate newVacationTemplate = VacationTemplate.builder()
                .userId(userId)
                .year(year)
                .maxVacationCount(maxVacationCount)
                .build();
        vacationTemplateRepository.save(newVacationTemplate);
    }

    @Transactional(readOnly = true)
    public Optional<VacationTemplateDTO> getVacationTemplateDTO(Long userId, int year) {
        return vacationTemplateRepository.getVacationTemplateByYear(userId, year)
                .map(vacationTemplateMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<VacationDTO> getVacationDTO(Long userId, Long vacationTemplateId, Long vacationId) {
        return vacationTemplateRepository.getVacationTemplateByTemplateId(userId, vacationTemplateId)
                .map(VacationTemplate::getVacations)
                .filter(vacations -> !CollectionUtils.isEmpty(vacations)).stream()
                .flatMap(Collection::stream)
                .filter(vacation -> vacation.getVacationId().equals(vacationId))
                .map(vacationTemplateMapper::toDTO)
                .findFirst();
    }

}
