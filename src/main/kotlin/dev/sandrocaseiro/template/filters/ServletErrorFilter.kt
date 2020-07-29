package dev.sandrocaseiro.template.filters

import dev.sandrocaseiro.template.services.LogService
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ServletErrorFilter(
    private val resolver: HandlerExceptionResolver,
    private val logService: LogService
): OncePerRequestFilter(), AccessDeniedHandler, AuthenticationEntryPoint {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            logService.build(request)
            chain.doFilter(request, response)
        } catch (e: Exception) {
            resolver.resolveException(request, response, null, e)
            logService.clear()
        }
    }

    override fun handle(request: HttpServletRequest, response: HttpServletResponse, e: AccessDeniedException) {
        resolver.resolveException(request, response, null, e)
        logService.clear()
    }

    override fun commence(request: HttpServletRequest, response: HttpServletResponse, e: AuthenticationException) {
        resolver.resolveException(request, response, null, e)
        logService.clear()
    }
}
