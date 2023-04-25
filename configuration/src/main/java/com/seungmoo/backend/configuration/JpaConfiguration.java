package com.seungmoo.backend.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.seungmoo.backend.domain.repositories"
)
@EnableJpaAuditing
@EntityScan("com.seungmoo.backend.domain.aggregates")
public class JpaConfiguration {
}
