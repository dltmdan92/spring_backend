package com.seungmoo.backend.domain.aggregates.vacation;

import com.seungmoo.backend.domain.aggregates.common.BaseEntity;
import com.seungmoo.backend.domain.aggregates.vacation.exceptions.VacationDaysExceedMaxCountException;
import com.seungmoo.backend.domain.aggregates.vacation.exceptions.VacationOnHolidayException;
import com.seungmoo.backend.domain.aggregates.vacation.exceptions.VacationPeriodConflictedException;
import com.seungmoo.backend.domain.aggregates.vacation.exceptions.VacationStartDateIsAfterThanTodayException;
import com.seungmoo.backend.domain.constants.VacationType;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "vacation_template")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Audited
@SQLDelete(sql = "UPDATE vacation_template SET deleted_at = current_timestamp WHERE vacation_template_id = ?")
@Where(clause = "deleted_at is null")
public class VacationTemplate extends BaseEntity {

    @Builder
    private VacationTemplate(int year, Long userId, int maxVacationCount) {
        this.year = year;
        this.userId = userId;
        this.maxVacationCount = maxVacationCount;
        this.vacations = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_template_id")
    private Long vacationTemplateId;

    @Column(name = "year")
    private int year;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "max_vacation_count")
    private int maxVacationCount;

    @OneToMany(mappedBy = "vacationTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacation> vacations;


    /**
     *
     * @param vacationType
     * @param startDate
     * @param endDate
     * @param comment
     *
     * @throws VacationStartDateIsAfterThanTodayException
     * @throws VacationOnHolidayException
     * @throws VacationDaysExceedMaxCountException
     * @throws VacationPeriodConflictedException
     */
    public void addVacation(VacationType vacationType, LocalDate startDate, LocalDate endDate, String comment) {
        addVacation(Vacation.of(vacationType, startDate, endDate, comment));
    }

    /**
     *
     * @param vacation
     * @throws VacationStartDateIsAfterThanTodayException
     * @throws VacationOnHolidayException
     * @throws VacationDaysExceedMaxCountException
     * @throws VacationPeriodConflictedException
     */
    public void addVacation(Vacation vacation) {
        validateNewVacation(vacation);

        this.vacations.add(vacation);
        vacation.setVacationTemplate(this);
    }

    private void validateNewVacation(Vacation newVacation) {
        // 휴가 날짜는 오늘 부터
        if (newVacation.getStartDate().isBefore(LocalDate.now())) {
            throw new VacationStartDateIsAfterThanTodayException();
        }

        // start_date or end_date가 공휴일인가?
        DayOfWeek weekDayOfStartDate = newVacation.getStartDate().getDayOfWeek();
        DayOfWeek weekDayOfEndDate = newVacation.getEndDate().getDayOfWeek();

        if (weekDayOfStartDate.equals(DayOfWeek.SATURDAY) || weekDayOfStartDate.equals(DayOfWeek.SUNDAY) ||
                weekDayOfEndDate.equals(DayOfWeek.SATURDAY) || weekDayOfEndDate.equals(DayOfWeek.SUNDAY)) {
            throw new VacationOnHolidayException();
        }

        BigDecimal asisVacationDays = vacations.stream()
                .map(Vacation::getDays)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        // maxVacationCount >= 기존 휴가 일 수 + 신규 휴가 일 수
        if (BigDecimal.valueOf(maxVacationCount).compareTo(asisVacationDays.add(newVacation.getDays())) < 0) {
            throw new VacationDaysExceedMaxCountException();
        }

        boolean newVacationConflicted = vacations.stream()
                .anyMatch(beforeVacation -> isNewVacationConflicted(newVacation, beforeVacation));

        // 기존 휴가 중에 기간 충돌되는 휴가 있는지?
        if (newVacationConflicted) {
            throw new VacationPeriodConflictedException();
        }

    }

    private boolean isNewVacationConflicted(Vacation newVacation, Vacation beforeVacation) {
        return (newVacation.getStartDate().isAfter(beforeVacation.getStartDate()) || newVacation.getStartDate().isEqual(beforeVacation.getStartDate())) && (newVacation.getStartDate().isBefore(beforeVacation.getEndDate()) || newVacation.getStartDate().isEqual(beforeVacation.getEndDate())) ||
                (newVacation.getEndDate().isAfter(beforeVacation.getStartDate()) || newVacation.getEndDate().isEqual(beforeVacation.getStartDate())) && (newVacation.getEndDate().isBefore(beforeVacation.getEndDate()) || newVacation.getEndDate().isEqual(beforeVacation.getEndDate()));
    }

}
