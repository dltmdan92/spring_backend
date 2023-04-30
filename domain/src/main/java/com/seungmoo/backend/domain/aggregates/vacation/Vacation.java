package com.seungmoo.backend.domain.aggregates.vacation;

import com.seungmoo.backend.domain.aggregates.common.BaseEntity;
import com.seungmoo.backend.domain.constants.VacationType;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Entity
@Table(name = "vacation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Audited
@SQLDelete(sql = "UPDATE vacation SET deleted_at = current_timestamp WHERE vacation_id = ?")
@Where(clause = "deleted_at is null")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Vacation extends BaseEntity {

    @Builder
    public Vacation(VacationType vacationType, LocalDate startDate, LocalDate endDate, String comment, VacationTemplate vacationTemplate) {
        this.vacationType = vacationType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.comment = comment;
        this.vacationTemplate = vacationTemplate;

        switch (vacationType) {
            case YONCHA -> this.days = BigDecimal.valueOf(calculateYonchaDays(startDate, endDate));
            case BANCHA -> this.days = BigDecimal.valueOf(0.5);
            case BANBANCHA -> this.days = BigDecimal.valueOf(0.25);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id")
    @EqualsAndHashCode.Include
    private Long vacationId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "vacation_type")
    private VacationType vacationType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "days")
    private BigDecimal days;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacation_template_id", nullable = false)
    private VacationTemplate vacationTemplate;

    static Vacation yoncha(LocalDate startDate, LocalDate endDate, String comment, VacationTemplate vacationTemplate) {
        return Vacation.builder()
                .vacationType(VacationType.YONCHA)
                .startDate(startDate)
                .endDate(endDate)
                .comment(comment)
                .vacationTemplate(vacationTemplate)
                .build();
    }

    static Vacation bancha(LocalDate startDate, String comment, VacationTemplate vacationTemplate) {
        return Vacation.builder()
                .vacationType(VacationType.BANCHA)
                .startDate(startDate)
                .endDate(startDate)
                .comment(comment)
                .vacationTemplate(vacationTemplate)
                .build();
    }

    static Vacation banbancha(LocalDate startDate, String comment, VacationTemplate vacationTemplate) {
        return Vacation.builder()
                .vacationType(VacationType.BANBANCHA)
                .startDate(startDate)
                .endDate(startDate)
                .comment(comment)
                .vacationTemplate(vacationTemplate)
                .build();
    }

    private int calculateYonchaDays(LocalDate startDate, LocalDate endDate) {
        int plusDays = 0;
        int days = 1;
        while (startDate.plusDays(plusDays).isBefore(endDate)) {
            DayOfWeek startDayOfWeek = startDate.plusDays(plusDays).getDayOfWeek();
            if (startDayOfWeek.equals(DayOfWeek.SATURDAY) || startDayOfWeek.equals(DayOfWeek.SUNDAY)) {
            } else {
                days++;
            }
            plusDays++;
        }

        return days;
    }
}
