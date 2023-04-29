package com.seungmoo.backend.api.presentation.user;

import com.seungmoo.backend.api.AdapterApiApplication;
import com.seungmoo.backend.api.service.user.protocols.requests.UserRegistryRequest;
import com.seungmoo.backend.configuration.utils.ObjectMapperUtils;
import com.seungmoo.backend.domain.constants.RoleType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest(classes = AdapterApiApplication.class)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Disabled
    @Test
    @DisplayName("사용자 생성 API 테스트")
    void createNewUser() throws Exception {
        UserRegistryRequest userRegistryRequest = UserRegistryRequest.builder()
                .username("seungmoo5")
                .password("1234qwer")
                .email("seungmoo5@example.com")
                .roles(List.of(RoleType.MEMBER))
                .build();
        this.mockMvc.perform(post("/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(ObjectMapperUtils.toString(userRegistryRequest)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

}