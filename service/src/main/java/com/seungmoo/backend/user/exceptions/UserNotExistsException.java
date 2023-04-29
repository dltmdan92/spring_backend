package com.seungmoo.backend.user.exceptions;

public class UserNotExistsException extends RuntimeException {
    public UserNotExistsException(String email) {
        super(email);
    }
}
