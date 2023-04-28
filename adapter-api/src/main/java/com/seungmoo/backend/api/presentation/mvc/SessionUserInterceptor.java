package com.seungmoo.backend.api.presentation.mvc;

import com.seungmoo.backend.api.service.user.exceptions.TokenIsEmptyException;
import com.seungmoo.backend.api.presentation.annotations.AuthRequired;
import com.seungmoo.backend.api.presentation.common.models.SessionUser;
import com.seungmoo.backend.api.service.user.providers.SessionProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
public class SessionUserInterceptor implements HandlerInterceptor {

    private final SessionProvider sessionProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            if (handlerMethod.getMethod().isAnnotationPresent(AuthRequired.class)) {
                String token = resolveToken(request)
                        .filter(StringUtils::hasText)
                        .filter(sessionProvider::validateToken)
                        .orElseThrow(TokenIsEmptyException::new);

                SessionUser sessionUser = sessionProvider.getSessionUser(token);
                request.setAttribute("sessionUser", sessionUser);
            }
        }

        return true;
    }

    private Optional<String> resolveToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty();
    }

}
