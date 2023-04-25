package com.seungmoo.backend.domain.aggregates.vacation;

import com.seungmoo.backend.domain.aggregates.common.BaseEntity;
import com.seungmoo.backend.domain.constants.VacationType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "vacation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Vacation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id")
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

}
