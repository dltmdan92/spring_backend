package com.seungmoo.backend.api.presentation.vacation;

import com.seungmoo.backend.api.presentation.annotations.AuthRequired;
import com.seungmoo.backend.api.presentation.templates.Resource;
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

    @Parameter(in = ParameterIn.HEADER, name = HttpHeaders.AUTHORIZATION, required = true, description = "'Bearer {token}' 형식으로 넣어주세요.")
    @AuthRequired
    @GetMapping("/auth")
    public Resource<List<String>> helloAuthRequired(SessionUser sessionUser) {
        log.info("sessionUser : " + sessionUser.getUsername());
        return Resource.<List<String>>builder()
                .data(List.of("hello", "hello1", "hello2", "auth"))
                .build();
    }

    @GetMapping("/non_auth")
    public Resource<List<String>> hello() {
        return Resource.<List<String>>builder()
                .data(List.of("hello", "hello1", "hello2", "non_auth"))
                .build();
    }

}
