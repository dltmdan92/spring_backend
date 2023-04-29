package com.seungmoo.backend.vacation.exceptions;

public class VacationRemoveFailedException extends RuntimeException {
    public VacationRemoveFailedException(String message) {
        super(message);
    }
}
