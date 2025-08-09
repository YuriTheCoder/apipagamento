package com.example.payments.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        String schemeName = "ApiKeyAuth";
        return new OpenAPI()
                .info(new Info().title("Payments API").version("v1").description("API de pagamentos"))
                .components(new Components().addSecuritySchemes(schemeName,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("X-API-KEY")
                ))
                .addSecurityItem(new SecurityRequirement().addList(schemeName));
    }
}


