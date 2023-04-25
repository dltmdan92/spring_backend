package com.seungmoo.backend.exceptions;

public class UserNotExistsException extends RuntimeException {
    public UserNotExistsException(String email) {
        super(email);
    }
}
