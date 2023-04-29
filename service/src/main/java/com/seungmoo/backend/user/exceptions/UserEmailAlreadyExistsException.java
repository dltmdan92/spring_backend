package com.seungmoo.backend.user.exceptions;

public class UserEmailAlreadyExistsException extends RuntimeException {
    public UserEmailAlreadyExistsException(String email) {
        super(email);
    }

}
