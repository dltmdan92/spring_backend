package com.seungmoo.backend.api.presentation.vacation;

import com.seungmoo.backend.api.presentation.annotations.AuthRequired;
import com.seungmoo.backend.api.service.user.models.SessionUser;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/vacations")
@RequiredArgsConstructor
public class VacationController {

    @AuthRequired
    @Parameter(in = ParameterIn.HEADER, name = HttpHeaders.AUTHORIZATION)
    @GetMapping("/auth")
    public List<String> helloAuthRequired(SessionUser sessionUser) {
        log.info("sessionUser : " + sessionUser.getUsername());
        return List.of("hello", "hello1", "hello2", "auth");
    }

    @GetMapping("/non_auth")
    public List<String> hello() {
        return List.of("hello", "hello1", "hello2", "non_auth");
    }

}
