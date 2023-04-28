package com.seungmoo.backend.api.presentation.mvc;

import com.seungmoo.backend.api.presentation.annotations.AuthRequired;
import com.seungmoo.backend.api.presentation.common.models.SessionUser;
import com.seungmoo.backend.configuration.utils.ObjectMapperUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class SessionUserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(SessionUser.class) && parameter.hasMethodAnnotation(AuthRequired.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        System.out.println("resolveArgument");
        Object sessionUser = webRequest.getAttribute("sessionUser", RequestAttributes.SCOPE_REQUEST);
        return null == sessionUser ? null : ObjectMapperUtils.convertValue(sessionUser, SessionUser.class);
    }
}