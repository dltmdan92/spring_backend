package com.seungmoo.backend.api.service.common.protocols.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Url {
    private HttpMethod method;
    private String href;
}
