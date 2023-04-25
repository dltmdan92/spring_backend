package com.seungmoo.backend.api.presentation.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seungmoo.backend.api.service.user.UserLoginService;
import com.seungmoo.backend.api.service.user.UserRegistryService;
import com.seungmoo.backend.api.service.user.requests.UserLoginRequest;
import com.seungmoo.backend.api.service.user.requests.UserRegistryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuthController {
    private final UserRegistryService userRegistryService;
    private final UserLoginService userLoginService;

    @PostMapping("/signup")
    public void signup(@RequestBody UserRegistryRequest userRegistryRequest) {
        userRegistryService.registUser(userRegistryRequest);
    }

    @PostMapping("/signin")
    public String getToken(@RequestBody UserLoginRequest userLoginRequest) throws JsonProcessingException {
        return userLoginService.getToken(userLoginRequest);
    }

}
