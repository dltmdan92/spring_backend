package com.seungmoo.backend.domain.repositories.user.custom;

import com.seungmoo.backend.domain.aggregates.user.QUser;
import com.seungmoo.backend.domain.aggregates.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class UserCustomRepositoryImpl extends QuerydslRepositorySupport implements UserCustomRepository {
    private final QUser user = QUser.user;

    public UserCustomRepositoryImpl() {
        super(User.class);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(
                from(user)
                .where(user.email.eq(email))
                .fetchOne());
    }

    @Override
    public List<User> findAllUsers(Pageable pageable) {
        return from(user)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(user.userId.asc())
                .fetch();
    }

}
