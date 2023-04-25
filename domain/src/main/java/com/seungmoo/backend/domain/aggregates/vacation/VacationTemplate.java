package com.seungmoo.backend.domain.aggregates.vacation;

import com.seungmoo.backend.domain.aggregates.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Year;
import java.util.List;


@Entity
@Table(name = "vacation_template")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class VacationTemplate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_template_id")
    private Long vacationTemplateId;

    @Column(name = "year")
    private Year year;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "max_vacation_count")
    private int maxVacationCount;

    @OneToMany(mappedBy = "vacationTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacation> vacations;

}
