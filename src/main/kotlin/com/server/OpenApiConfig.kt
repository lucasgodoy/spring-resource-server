package com.server

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class OpenApiConfig {

    @Bean
    fun customOpenAPI(@Value("\${springDoc.apiVersion}") apiVersion: String): OpenAPI =
        OpenAPI()
            .info(
                Info()
                    .title("Resource Server example API")
                    .description("Version $apiVersion of the REST API that supports both JWT and Opaque Token.")
                    .version(apiVersion)
            )
            .components(
                Components()
                    .addSecuritySchemes(
                        API_SCHEMA,
                        SecurityScheme()
                            .scheme(BEARER_SCHEMA)
                            .type(SecurityScheme.Type.HTTP)
                            .bearerFormat(BEARER_FORMAT)
                            .name(API_SCHEMA)
                    )
            )
            .addSecurityItem(SecurityRequirement().addList("api"))

    companion object {
        private const val API_SCHEMA = "api"
        private const val BEARER_FORMAT = "jwt"
        private const val BEARER_SCHEMA = "bearer"
    }
}
