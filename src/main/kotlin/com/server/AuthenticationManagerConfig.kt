package com.server

import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManagerResolver
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
import org.springframework.security.oauth2.server.resource.authentication.JwtBearerTokenAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector

/**
 * @author Lucas Godoy
 */
@Configuration
internal class AuthenticationManagerConfig(private val properties: ApplicationProperties) :
    AuthenticationManagerResolver<HttpServletRequest> {

    private val jwtProviderManager by lazy { jwtProviderManager() }
    private val opaqueTokenProviderManager by lazy { opaqueTokenProviderManager() }

    override fun resolve(request: HttpServletRequest) =
        if (isJwt(request)) {
            println("It's a JWT token")
            jwtProviderManager ?: throw InvalidTokenException("JWT token is not supported.")
        } else {
            println("It's an opaque token")
            opaqueTokenProviderManager ?: throw InvalidTokenException("Opaque token is not supported.")
        }

    private fun isJwt(request: HttpServletRequest) =
        request.getHeader(AUTHORIZATION_HEADER).run { contains("Bearer") && count { it == '.' } == 2 }

    fun jwtProviderManager(): ProviderManager? {
        return properties.jwt?.let {
            val jwtDecoder = NimbusJwtDecoder.withJwkSetUri(it.jwkSetUri).build()
            val authenticationProvider = JwtAuthenticationProvider(jwtDecoder).apply {
                setJwtAuthenticationConverter(JwtBearerTokenAuthenticationConverter())
            }
            ProviderManager(authenticationProvider)
        }
    }

    fun opaqueTokenProviderManager(): ProviderManager? {
        return properties.opaque?.let {
            val introspectionClient = NimbusOpaqueTokenIntrospector(it.introspectionUri, it.clientId, it.clientSecret)
            return ProviderManager(OpaqueTokenAuthenticationProvider(introspectionClient))
        }
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
    }
}