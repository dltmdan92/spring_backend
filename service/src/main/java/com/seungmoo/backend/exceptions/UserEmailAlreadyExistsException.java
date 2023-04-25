package com.seungmoo.backend.exceptions;

public class UserEmailAlreadyExistsException extends RuntimeException {
    public UserEmailAlreadyExistsException(String email) {
        super(email);
    }

}
