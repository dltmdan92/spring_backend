package com.seungmoo.backend.domain.repositories.user.custom;

import com.seungmoo.backend.domain.aggregates.user.User;

import java.util.Optional;

public interface UserCustomRepository {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String encodedPassword);
}
