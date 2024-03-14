package com.crosemont.dti.g26.stageavenirapi.controlleurs

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class APIControleur {

    @GetMapping("/")
    fun index() = "Service web du service de gestion de stages"



    @GetMapping("/utilisateur")
    fun lireUtilisateur(principal: Principal?): String? {
        if (principal != null) {
            return "Bonjour, " + principal.name
        } else {
            return null
        }
    }


    @GetMapping("/jeton")
    fun getPrincipalInfo(principal: JwtAuthenticationToken): Map<String, Any> {
        val authorities: Collection<String> = principal.authorities
            .stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .toList()

        val info: MutableMap<String, Any> = HashMap()
        info["name"] = principal.name
        info["authorities"] = authorities
        info["tokenAttributes"] = principal.tokenAttributes

        return info
    }

}