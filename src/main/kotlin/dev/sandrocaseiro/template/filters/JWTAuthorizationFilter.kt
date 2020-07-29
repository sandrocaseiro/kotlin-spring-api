package dev.sandrocaseiro.template.filters

import dev.sandrocaseiro.template.properties.JwtProperties
import dev.sandrocaseiro.template.security.TokenUser
import dev.sandrocaseiro.template.security.UserPrincipal
import dev.sandrocaseiro.template.services.JwtAuthService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(
    private val jwtAuthService: JwtAuthService,
    private val jwtProperties: JwtProperties,
    authenticationManager: AuthenticationManager
) : BasicAuthenticationFilter(authenticationManager) {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header: String? = request.getHeader(jwtProperties.header)
        if (header.isNullOrEmpty() || !header.startsWith(jwtProperties.tokenPrefix)) {
            chain.doFilter(request, response)
            return
        }

        SecurityContextHolder.getContext().authentication = getAuthentication(header)
        chain.doFilter(request, response)
    }

    private fun getAuthentication(header: String): UsernamePasswordAuthenticationToken {
        val tokenUser: TokenUser = jwtAuthService.parseBearerToken(header.replace("${jwtProperties.tokenPrefix} ", ""))
        val principal = UserPrincipal(
            tokenUser.id,
            tokenUser.name,
            tokenUser.username,
            tokenUser.groupId,
            tokenUser.roles.toSet()
        )

        return UsernamePasswordAuthenticationToken(principal, null, emptyList())
    }
}
