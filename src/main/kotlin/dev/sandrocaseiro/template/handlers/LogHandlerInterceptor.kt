package dev.sandrocaseiro.template.handlers

import dev.sandrocaseiro.template.services.LogService
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LogHandlerInterceptor(
    private val logService: LogService
): HandlerInterceptor {
    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        logService.clear()
    }
}
