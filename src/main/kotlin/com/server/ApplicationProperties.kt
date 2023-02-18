package com.server

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author Lucas Godoy
 */
@ConfigurationProperties(prefix = "security")
internal data class ApplicationProperties(
    val jwt: Jwt? = null,
    val opaque: OpaqueToken? = null
) {
    data class Jwt(val issuerUri: String, val jwkSetUri: String)

    data class OpaqueToken(val introspectionUri: String, val clientId: String, val clientSecret: String)
}
