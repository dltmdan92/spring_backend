package com.seungmoo.backend.api.presentation.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seungmoo.backend.api.service.common.protocols.response.Resource;
import com.seungmoo.backend.api.service.user.UserLoginService;
import com.seungmoo.backend.api.service.user.UserRegistryService;
import com.seungmoo.backend.api.service.user.protocols.requests.UserLoginRequest;
import com.seungmoo.backend.api.service.user.protocols.requests.UserRegistryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuthController {
    private final UserRegistryService userRegistryService;
    private final UserLoginService userLoginService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody UserRegistryRequest userRegistryRequest) {
        userRegistryService.registUser(userRegistryRequest);
    }

    @PostMapping("/signin")
    public Resource<String> makeToken(@RequestBody UserLoginRequest userLoginRequest) throws JsonProcessingException {
        return Resource.<String>builder()
                .data(userLoginService.getToken(userLoginRequest))
                .build();
    }

}
