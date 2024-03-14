package com.crosemont.dti.g26.stageavenirapi

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringDocConfiguration {

    @Bean
    fun apiInfo(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("API du service de gestion des stages")
                    .description("API du service de gestion des stages")
                    .version("1.0.0")
            )
    }
}