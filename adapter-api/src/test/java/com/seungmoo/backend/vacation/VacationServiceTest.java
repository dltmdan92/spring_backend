package com.seungmoo.backend.vacation;

import com.seungmoo.backend.domain.aggregates.vacation.Vacation;
import com.seungmoo.backend.domain.aggregates.vacation.VacationTemplate;
import com.seungmoo.backend.domain.aggregates.vacation.exceptions.VacationDaysExceedMaxCountException;
import com.seungmoo.backend.domain.aggregates.vacation.exceptions.VacationOnHolidayException;
import com.seungmoo.backend.domain.aggregates.vacation.exceptions.VacationPeriodConflictedException;
import com.seungmoo.backend.domain.aggregates.vacation.exceptions.VacationStartDateIsAfterThanTodayException;
import com.seungmoo.backend.domain.constants.VacationType;
import com.seungmoo.backend.domain.repositories.vacation.VacationTemplateRepository;
import com.seungmoo.backend.vacation.exceptions.VacationAddFailedException;
import com.seungmoo.backend.vacation.mappers.VacationTemplateMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VacationServiceTest {

    @InjectMocks
    private VacationService vacationService;

    @Mock
    private VacationTemplateRepository vacationTemplateRepository;

    @Spy
    private VacationTemplateMapper vacationTemplateMapper = Mappers.getMapper(VacationTemplateMapper.class);

    @Nested
    @DisplayName("add vacation test")
    class addVacationTest {
        @Test
        @DisplayName("add vacation failed - vacation template not exists!")
        void addVacationFailedVacationTemplateNotExists() {
            given(vacationTemplateRepository.getVacationTemplateByTemplateId(any(), any())).willReturn(Optional.empty());

            assertThrows(VacationAddFailedException.class, () -> vacationService.addVacation(1L, 1L, VacationType.YONCHA, LocalDate.of(2023, 5,2), LocalDate.of(2023,5,2), "연차 입니다."));
        }

        @Test
        @DisplayName("add vacation failed - vacation startdate is after than today exception")
        void addVacationFailedVacationStartDateIsAfterThanTodayException() {
            given(vacationTemplateRepository.getVacationTemplateByTemplateId(any(), any())).willReturn(Optional.of(
                    VacationTemplate.builder()
                            .year(2023)
                            .userId(1L)
                            .maxVacationCount(15)
                            .build()));

            assertThrows(VacationStartDateIsAfterThanTodayException.class, () -> vacationService.addVacation(1L, 1L, VacationType.YONCHA, LocalDate.of(2023, 4,28), LocalDate.of(2023,4,28), "연차 입니다."));
        }

        @Test
        @DisplayName("add vacation failed - vacation on holiday exception")
        void addVacationFailedVacationOnHolidayException() {
            given(vacationTemplateRepository.getVacationTemplateByTemplateId(any(), any())).willReturn(Optional.of(
                    VacationTemplate.builder()
                            .year(2023)
                            .userId(1L)
                            .maxVacationCount(15)
                            .build()));

            assertThrows(VacationOnHolidayException.class, () -> vacationService.addVacation(1L, 1L, VacationType.YONCHA, LocalDate.of(2023, 6,10), LocalDate.of(2023,6,10), "연차 입니다."));
        }

        @Test
        @DisplayName("add vacation failed - VacationDaysExceedMaxCountException")
        void addVacationFailedVacationDaysExceedMaxCountException() {
            VacationTemplate mockVacationTemplate = VacationTemplate.builder()
                    .year(2023)
                    .userId(1L)
                    .maxVacationCount(15)
                    .build();

            // 5일씩
            Vacation v1 = Vacation.yoncha(LocalDate.of(2023, 6, 12), LocalDate.of(2023, 6, 16), "", mockVacationTemplate);
            Vacation v2 = Vacation.yoncha(LocalDate.of(2023, 6, 19), LocalDate.of(2023, 6, 23), "", mockVacationTemplate);
            Vacation v3 = Vacation.yoncha(LocalDate.of(2023, 6, 26), LocalDate.of(2023, 6, 30), "", mockVacationTemplate);

            mockVacationTemplate.addVacation(v1);
            mockVacationTemplate.addVacation(v2);
            mockVacationTemplate.addVacation(v3);

            given(vacationTemplateRepository.getVacationTemplateByTemplateId(any(), any())).willReturn(Optional.of(mockVacationTemplate));

            assertThrows(VacationDaysExceedMaxCountException.class, () -> vacationService.addVacation(1L, 1L, VacationType.YONCHA, LocalDate.of(2023,7,3), LocalDate.of(2023,7,3), ""));
        }

        @Test
        @DisplayName("add vacation failed - VacationPeriodConflictedException")
        void addVacationFailedVacationPeriodConflictedException() {
            VacationTemplate mockVacationTemplate = VacationTemplate.builder()
                    .year(2023)
                    .userId(1L)
                    .maxVacationCount(15)
                    .build();

            Vacation v1 = Vacation.yoncha(LocalDate.of(2023, 6, 12), LocalDate.of(2023, 6, 16), "", mockVacationTemplate);
            Vacation v2 = Vacation.yoncha(LocalDate.of(2023, 6, 19), LocalDate.of(2023, 6, 23), "", mockVacationTemplate);

            mockVacationTemplate.addVacation(v1);
            mockVacationTemplate.addVacation(v2);

            given(vacationTemplateRepository.getVacationTemplateByTemplateId(any(), any())).willReturn(Optional.of(mockVacationTemplate));

            assertThrows(VacationPeriodConflictedException.class, () -> vacationService.addVacation(1L, 1L, VacationType.YONCHA, LocalDate.of(2023,6,13), LocalDate.of(2023,6,14), ""));
        }

    }

}