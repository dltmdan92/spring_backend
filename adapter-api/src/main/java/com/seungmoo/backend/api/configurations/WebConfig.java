package com.seungmoo.backend.api.configurations;

import com.seungmoo.backend.api.presentation.mvc.SessionUserArgumentResolver;
import com.seungmoo.backend.api.presentation.mvc.SessionUserInterceptor;
import com.seungmoo.backend.api.service.user.providers.SessionProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final SessionProvider sessionProvider;
    private final SessionUserArgumentResolver sessionUserArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionUserInterceptor(sessionProvider))
                .addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(sessionUserArgumentResolver);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz.anyRequest().permitAll());
        return http.build();
    }
}
