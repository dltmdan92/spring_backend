package com.seungmoo.backend.vacation;

import com.seungmoo.backend.domain.aggregates.vacation.VacationTemplate;
import com.seungmoo.backend.domain.constants.VacationType;
import com.seungmoo.backend.domain.repositories.vacation.VacationTemplateRepository;
import com.seungmoo.backend.vacation.dtos.VacationDTO;
import com.seungmoo.backend.vacation.dtos.VacationTemplateDTO;
import com.seungmoo.backend.vacation.exceptions.VacationAddFailedException;
import com.seungmoo.backend.vacation.exceptions.VacationRemoveFailedException;
import com.seungmoo.backend.vacation.mappers.VacationTemplateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
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
        vacationTemplateRepository.save(VacationTemplate.of(year, userId, maxVacationCount));
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

    @Transactional
    public void addVacation(Long userId, Long vacationTemplateId, VacationType vacationType, LocalDate startDate, LocalDate endDate, String comment) {
        Optional<VacationTemplate> vacationTemplate = vacationTemplateRepository.getVacationTemplateByTemplateId(userId, vacationTemplateId);
        if (vacationTemplate.isPresent()) {
            vacationTemplate.get().addVacation(vacationType, startDate, endDate, comment);
        } else {
            throw new VacationAddFailedException("vacation template이 존재하지 않습니다!");
        }
    }

    @Transactional
    public void deleteVacation(Long userId, Long vacationTemplateId, Long vacationId) {
        Optional<VacationTemplate> vacationTemplate = vacationTemplateRepository.getVacationTemplateByTemplateId(userId, vacationTemplateId);

        if (vacationTemplate.isPresent()) {
            vacationTemplate.get().getVacations().removeIf(vacation -> vacation.getVacationId().equals(vacationId));
        } else {
            throw new VacationRemoveFailedException("vacation template이 존재하지 않습니다!");
        }
    }
}
