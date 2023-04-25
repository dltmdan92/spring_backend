package com.seungmoo.backend.domain.repositories.user.custom;

import com.seungmoo.backend.domain.aggregates.user.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

public class UserCustomRepositoryImpl extends QuerydslRepositorySupport implements UserCustomRepository {

    public UserCustomRepositoryImpl() {
        super(User.class);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String encodedPassword) {
        return Optional.empty();
    }
}
