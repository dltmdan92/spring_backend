package com.seungmoo.backend.domain.aggregates.vacation;

import com.seungmoo.backend.domain.aggregates.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "vacation_template")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Audited
@SQLDelete(sql = "UPDATE vacation_template SET deleted_at = current_timestamp WHERE vacation_template_id = ?")
@Where(clause = "deleted_at is null")
public class VacationTemplate extends BaseEntity {

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

}
