package com.sandrocaseiro.template.controllers

import com.sandrocaseiro.template.exceptions.*
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest

@Controller
class CustomErrorController : ErrorController {
    @RequestMapping("/error")
    fun error(request: HttpServletRequest) {
        val exception = request.getAttribute("javax.servlet.error.exception");
        if (exception != null)
            throw exception as Throwable

        val attr = request.getAttribute("javax.servlet.error.status_code");
        var statusCode: Int = INTERNAL_SERVER_ERROR.value()
        if (attr != null)
            statusCode = attr.toString().toInt();

        when (valueOf(statusCode)) {
            UNAUTHORIZED -> throw UnauthorizedException()
            FORBIDDEN -> throw ForbiddenException()
            NOT_FOUND -> throw NotFoundException()
            METHOD_NOT_ALLOWED -> throw MethodNotAllowedException()
            UNSUPPORTED_MEDIA_TYPE -> throw UnsupportedMediaTypeException()
            else -> throw RuntimeException()
        }
    }

    override fun getErrorPath(): String = "/error"
}
