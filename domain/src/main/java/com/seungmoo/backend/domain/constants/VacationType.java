package com.seungmoo.backend.domain.constants;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum VacationType {
    YONCHA(BigDecimal.valueOf(1.0)),
    BANCHA(BigDecimal.valueOf(0.5)),
    BANBANCHA(BigDecimal.valueOf(0.25));

    private final BigDecimal day;

    VacationType(BigDecimal day) {

        this.day = day;
    }
}
