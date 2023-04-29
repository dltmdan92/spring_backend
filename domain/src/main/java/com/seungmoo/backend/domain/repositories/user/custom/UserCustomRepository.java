package com.seungmoo.backend.domain.repositories.user.custom;

import com.seungmoo.backend.domain.aggregates.user.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserCustomRepository {
    Optional<User> findByEmail(String email);

    List<User> findAllUsers(Pageable pageable);
}
