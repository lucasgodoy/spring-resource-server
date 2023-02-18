package com.server

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Lucas Godoy
 */
@RestController
internal class Controller {

    @GetMapping("/some_resource")
    fun getSomeResource(@AuthenticationPrincipal principal: OAuth2AuthenticatedPrincipal): String {
        return String.format("Hello, ${principal.name}!")
    }
}