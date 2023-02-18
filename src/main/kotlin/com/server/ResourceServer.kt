package com.server

import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManagerResolver
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

/**
 * @author Lucas Godoy
 */
@Configuration
@EnableWebSecurity
internal class ResourceServer(
    private val authenticationManagerResolver: AuthenticationManagerResolver<HttpServletRequest>
) {
    @Bean
    fun filterChain(httpSecurity: HttpSecurity, properties: ApplicationProperties): SecurityFilterChain {
        httpSecurity
            .csrf()
            .and()
            .sessionManagement { SessionCreationPolicy.STATELESS }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("/resource-server-api-docs/**").permitAll()
                    .anyRequest().authenticated()
            }
            .httpBasic().disable()
            .oauth2ResourceServer {
                it.authenticationManagerResolver(authenticationManagerResolver)
            }
        return httpSecurity.build()
    }
}
