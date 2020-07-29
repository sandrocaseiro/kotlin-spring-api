package dev.sandrocaseiro.template.filters

import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.exceptions.AppException
import dev.sandrocaseiro.template.properties.JwtProperties
import dev.sandrocaseiro.template.security.TokenAuthResponse
import dev.sandrocaseiro.template.security.TokenUser
import dev.sandrocaseiro.template.services.JwtAuthService
import dev.sandrocaseiro.template.utils.serialize
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(
    private val authManager: AuthenticationManager,
    private val jwtAuthService: JwtAuthService,
    private val jwtProperties: JwtProperties
) : UsernamePasswordAuthenticationFilter() {
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val header: String? = request.getHeader(jwtProperties.header)
        if (!header.isNullOrEmpty() && header.startsWith(jwtProperties.tokenPrefix)) {
            val token: String = header.replace("${jwtProperties.tokenPrefix} ", "")
            if (jwtAuthService.isRefreshToken(token)) {
                val principal: TokenUser = jwtAuthService.parseBearerToken(token)
                return authManager.authenticate(UsernamePasswordAuthenticationToken(principal, null, emptyList()))
            }
        }

        if (!request.contentType.startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) AppErrors.UNSUPPORTED_MEDIA_TYPE.throws()

        val username: String = request.getParameter("username")
        val password: String = request.getParameter("password")
        return authManager.authenticate(UsernamePasswordAuthenticationToken(username, password, emptyList()))
    }

    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
        val user = authResult.principal as TokenUser
        val tokenResp: TokenAuthResponse = jwtAuthService.generateTokenResponse(user)

        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        response.writer.write(tokenResp.serialize() ?: "")
    }

    override fun unsuccessfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, failed: AuthenticationException) {
        throw AppException.of(AppErrors.UNAUTHORIZED_ERROR, failed)
    }
}
