package com.seungmoo.backend.vacation.exceptions;

public class VacationAddFailedException extends RuntimeException{
    public VacationAddFailedException(String message) {
        super(message);
    }
}
