package com.example.restaurant.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI restaurantOpenApi() {
        return new OpenAPI().info(new Info().title("Restaurant Rating API").description("REST API для системы оценки ресторанов").version("1.0.0"));
    }
}
