package com.seungmoo.backend.domain.repositories.user;

import com.seungmoo.backend.domain.aggregates.user.User;
import com.seungmoo.backend.domain.repositories.user.custom.UserCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
}
