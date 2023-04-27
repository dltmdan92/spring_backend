package com.seungmoo.backend.api.presentation.user;

import com.seungmoo.backend.api.AdapterApiApplication;
import com.seungmoo.backend.api.service.user.requests.UserLoginRequest;
import com.seungmoo.backend.api.service.user.requests.UserRegistryRequest;
import com.seungmoo.backend.configuration.utils.ObjectMapperUtils;
import com.seungmoo.backend.domain.constants.RoleType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = AdapterApiApplication.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Disabled
    @Test
    @DisplayName("회원가입 real 테스트")
    void testSignupReal() throws Exception {
        UserRegistryRequest request = UserRegistryRequest.builder()
                .username("seungmoo")
                .password("1234qwer")
                .email("seungmoo@example.com")
                .roles(List.of(RoleType.ADMIN, RoleType.MEMBER))
                .build();

        this.mockMvc.perform(post("/v1/signup")
                .content(ObjectMapperUtils.toString(request))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))

                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인(makeToken) API 테스트")
    void testLoginMakeToken() throws Exception {
        UserLoginRequest request = UserLoginRequest.builder()
                .email("seungmoo@example.com")
                .password("1234qwer")
                .build();

        MockHttpServletResponse response = this.mockMvc.perform(post("/v1/signin")
                        .content(ObjectMapperUtils.toString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        String token = response.getContentAsString();

        this.mockMvc.perform(get("/v1/vacations/auth")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());
    }

}