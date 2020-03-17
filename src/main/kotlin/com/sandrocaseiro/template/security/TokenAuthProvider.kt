package com.sandrocaseiro.template.security

import com.sandrocaseiro.template.services.JwtAuthService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class TokenAuthProvider(private val jwtAuthService: JwtAuthService) : AuthenticationProvider {
    override fun supports(clazz: Class<*>): Boolean = clazz == UsernamePasswordAuthenticationToken::class.java

    override fun authenticate(authentication: Authentication): Authentication {
        if (authentication.principal is TokenUser)
            return authentication

        val user = jwtAuthService.loadUserByUsername(authentication.principal.toString()) as TokenUser

        if (authentication.credentials.toString().isBlank()
            || !CustomPasswordEncoder.matches(authentication.credentials as CharSequence, user.password))
            throw BadCredentialsException("Invalid credentials")

        return UsernamePasswordAuthenticationToken(user, null, emptyList())
    }
}
