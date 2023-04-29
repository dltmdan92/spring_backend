package com.seungmoo.backend.api.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        String itemName = "Bearer JWT";
        SecurityScheme bearer = new SecurityScheme()
                .name(itemName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        SecurityRequirement securityItem = new SecurityRequirement().addList(itemName);

        return new OpenAPI()
                .components(new Components().addSecuritySchemes(itemName, bearer))
                .addSecurityItem(securityItem)
                .info(new Info()
                        .title("backend-seungmoo API")
                        .description("backend-seungmoo API"));
    }
}
