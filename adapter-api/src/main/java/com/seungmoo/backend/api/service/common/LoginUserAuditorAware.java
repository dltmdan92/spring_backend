package com.seungmoo.backend.api.service.common;

import com.seungmoo.backend.api.presentation.common.models.SessionUser;
import com.seungmoo.backend.configuration.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoginUserAuditorAware implements AuditorAware<String> {

    private final NativeWebRequest webRequest;

    @Override
    public Optional<String> getCurrentAuditor() {
        Object sessionUser = webRequest.getAttribute("sessionUser", RequestAttributes.SCOPE_REQUEST);

        return Optional.ofNullable(sessionUser)
                .map(o -> ObjectMapperUtils.convertValue(o, SessionUser.class))
                .map(SessionUser::getUserId)
                .map(String::valueOf);
    }
}
