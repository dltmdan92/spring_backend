package com.seungmoo.backend.domain.aggregates.vacation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VacationTest {

    @Nested
    @DisplayName("일 수 계산기")
    class calculateDays {

        @Nested
        @DisplayName("연차")
        class Yoncha {

            @Test
            @DisplayName("1일")
            void calculateOneDay() {
                int yonchaDays = ReflectionTestUtils.invokeMethod(new Vacation(), "calculateYonchaDays", LocalDate.of(2023, 6, 12), LocalDate.of(2023, 6, 12));
                assertEquals(1, yonchaDays);
            }

            @Test
            @DisplayName("3일")
            void calculateThreeDay() {
                int yonchaDays = ReflectionTestUtils.invokeMethod(new Vacation(), "calculateYonchaDays", LocalDate.of(2023, 6, 12), LocalDate.of(2023, 6, 14));
                assertEquals(3, yonchaDays);
            }

            @Test
            @DisplayName("주말 제외하면 3일")
            void calculateThreeDayWithoutHolidays() {
                int yonchaDays = ReflectionTestUtils.invokeMethod(new Vacation(), "calculateYonchaDays", LocalDate.of(2023, 6, 15), LocalDate.of(2023, 6, 19));
                assertEquals(3, yonchaDays);
            }

        }

    }

}